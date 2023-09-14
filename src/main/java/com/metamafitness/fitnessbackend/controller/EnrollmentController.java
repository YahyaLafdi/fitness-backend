package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.dto.OrderDto;
import com.metamafitness.fitnessbackend.dto.ProgramEnrollmentDto;
import com.metamafitness.fitnessbackend.dto.UserDto;
import com.metamafitness.fitnessbackend.exception.*;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.Program;
import com.metamafitness.fitnessbackend.model.ProgramEnrollment;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.PaymentService;
import com.metamafitness.fitnessbackend.service.ProgramEnrollmentService;
import com.metamafitness.fitnessbackend.service.ProgramService;
import com.metamafitness.fitnessbackend.service.UserService;
import com.paypal.orders.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.*;
import static com.metamafitness.fitnessbackend.common.CoreConstant.Pagination.DEFAULT_PAGE_NUMBER;
import static com.metamafitness.fitnessbackend.common.CoreConstant.Pagination.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController extends GenericController<ProgramEnrollment, ProgramEnrollmentDto> {

    private final ProgramService programService;
    private final ProgramEnrollmentService programEnrollmentService;

    private final PaymentService paymentService;

    private final UserService userService;

    public EnrollmentController(ProgramService programService, ProgramEnrollmentService programEnrollmentService, PaymentService paymentService, UserService userService) {
        this.programService = programService;
        this.programEnrollmentService = programEnrollmentService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/trainer/users")
    public ResponseEntity<List<UserDto>> findUsersByTrainer(@RequestParam(value = "page", defaultValue = "" + DEFAULT_PAGE_NUMBER) Integer page,
                                                            @RequestParam(value = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) Integer size) throws BusinessException {
        final Long currentUserId = getCurrentUserId();
        List<User> enrollments = userService.findNewEnrolledUsers(currentUserId, page, size);

        List<UserDto> dto = enrollments.stream().map((user) -> getModelMapper().map(user, UserDto.class)).collect(Collectors.toList());
        long totalPrograms = userService.countNewEnrolledUsers(currentUserId);
        int totalPages = (int) Math.ceil((double) totalPrograms / size);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dto);
    }

    @GetMapping("/trainer")
    public ResponseEntity<List<ProgramEnrollmentDto>> findByTrainer(@RequestParam(value = "page", defaultValue = "" + DEFAULT_PAGE_NUMBER) Integer page,
                                                                    @RequestParam(value = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) Integer size) throws BusinessException {
        Pageable pageable = PageRequest.of(page, size);
        final Long currentUserId = getCurrentUserId();
        List<ProgramEnrollment> enrollments = programEnrollmentService.findByProgramCreator(currentUserId, pageable);

        List<ProgramEnrollmentDto> dto = enrollments.stream().map(this::convertToDto).collect(Collectors.toList());
        long totalPrograms = programEnrollmentService.countByProgramCreator(currentUserId);
        int totalPages = (int) Math.ceil((double) totalPrograms / size);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dto);
    }

    @GetMapping("/exists/{programID}")
    ResponseEntity<ProgramEnrollmentDto> enrolled(@PathVariable Long programID) {
        ProgramEnrollment programEnrollment = programEnrollmentService.findByUserAndProgram(getCurrentUserId(), programID);
        if (Objects.isNull(programEnrollment)) {
            throw new ElementNotFoundException(new ElementNotFoundException(), NOT_FOUND, new Object[]{programID});
        }
        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(programEnrollment));
    }

    @PostMapping("create-order/{programId}")
    ResponseEntity<OrderDto> createEnrollmentOrder(@PathVariable("programId") Long programId) throws IOException, ElementAlreadyExistException, UserAlreadyEnrolled {
        final Program program = programService.findById(programId);
        if (!GenericEnum.ProgramState.APPROVED.equals(program.getState())) {
            throw new UnauthorizedPurchaseException(new UnauthorizedPurchaseException(), UNAUTHORIZED_PROGRAM_PURCHASE, null);
        }
        final Long currentUserId = getCurrentUserId();
        final ProgramEnrollment programEnrollment = programEnrollmentService.findByUserAndProgram(currentUserId, program.getId());
        if (Objects.nonNull(programEnrollment)) {
            throw new UserAlreadyEnrolled(new UserAlreadyEnrolled(), AUTHORIZATION_USER_ALREADY_ENROLLED, null);
        }
        Order order = paymentService.createOrder(program);

        String redirectUrl = order.links().stream()
                .filter(link -> "approve".equals(link.rel()))
                .findFirst()
                .orElseThrow(() -> new LinkNotFoundException(new LinkNotFoundException(), APPROVAL_LINK_NOT_FOUND, null))
                .href();

        return ResponseEntity
                .status(HttpStatus.CREATED).
                body(OrderDto
                        .builder()
                        .id(order.id())
                        .redirectUrl(redirectUrl)
                        .status(order.status())
                        .createdAt(order.createTime())
                        .build());
    }

    @PostMapping("/complete-order")
    public ResponseEntity<ProgramEnrollmentDto> completeOrder(@RequestParam String orderId) throws IOException, TransactionAlreadyCompletedException {
        ProgramEnrollment enrollment = paymentService.completeOrder(orderId, getCurrentUser());
        ProgramEnrollment saved = programEnrollmentService.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(saved));
    }

}
