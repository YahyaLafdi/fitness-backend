package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.dto.JoinDto;
import com.metamafitness.fitnessbackend.dto.JoinTreatDto;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.Join;
import com.metamafitness.fitnessbackend.model.TrainerRole;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.JoinService;
import com.metamafitness.fitnessbackend.service.StorageService;
import com.metamafitness.fitnessbackend.service.TrainerRoleService;
import com.metamafitness.fitnessbackend.validator.ValidPreviewPictures;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/joins")
@AllArgsConstructor
public class JoinController extends GenericController<Join, JoinDto> {

    private final JoinService joinService;

    private final TrainerRoleService trainerRoleService;
    private final StorageService storageService;


    @PostMapping("/join-us")
    public ResponseEntity<JoinDto> requestJoinAsTrainer(@RequestPart(value = "join") @Valid JoinDto joinDto,@Valid @ValidPreviewPictures @RequestPart(value = "documents") List<MultipartFile> documents) {
        final User user = getCurrentUser();
        Join join = convertToEntity(joinDto);
        List<String> previewImageUrls = storageService.storeFiles(documents);
        join.setDocuments(previewImageUrls);
        join.setApproved(Boolean.FALSE);
        join.setSender(user);
        return new ResponseEntity<>(convertToDto(joinService.save(join)), HttpStatus.CREATED);
    }

    @GetMapping("/requests")
    public ResponseEntity<List<JoinDto>> findAll(@RequestParam(name = "page", required = false) Integer page,
                                                 @RequestParam(name = "size", required = false) Integer size) {
        if(page == null) page = CoreConstant.Pagination.DEFAULT_PAGE_NUMBER;
        if(size == null) size = CoreConstant.Pagination.DEFAULT_PAGE_SIZE;

        List<Join> joins = joinService.findAll(page, size).toList();
        List<JoinDto> joinsDtos = joins.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(joinsDtos, HttpStatus.OK);
    }

    @PatchMapping("/treat/{id_request}")
    public ResponseEntity<JoinDto> treatRequestJoinAsTrainer(@PathVariable("id_request") Long id, @RequestBody JoinTreatDto joinTreatDto) {
        final Boolean decision = joinTreatDto.getAccepted();
        Join joinRequest = joinService.findById(id);
        if (joinRequest.getApproved())
            throw new BusinessException(new BusinessException(), CoreConstant.Exception.JOIN_REQUEST_ALREADY_HANDLED, null);
        if (!decision) {
            joinService.delete(joinRequest.getId());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        final TrainerRole trainerRole = trainerRoleService.findByName(GenericEnum.RoleName.TRAINER);
        joinRequest.getSender().addRole(trainerRole);
        joinRequest.setApproved(Boolean.TRUE);
        Join accepted = joinService.update(id, joinRequest);
        return new ResponseEntity<>(convertToDto(accepted), HttpStatus.CREATED);
    }


}
