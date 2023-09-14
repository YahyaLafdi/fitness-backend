package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.dto.AuthenticatedResetPasswordRequest;
import com.metamafitness.fitnessbackend.dto.SearchDto;
import com.metamafitness.fitnessbackend.dto.UserDto;
import com.metamafitness.fitnessbackend.dto.UserPatchDto;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.exception.UnauthorizedException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.StorageService;
import com.metamafitness.fitnessbackend.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController extends GenericController<User, UserDto> {

    private final UserService userService;

    private final StorageService storageService;

    @Override
    public ModelMapper getModelMapper() {
        return super.getModelMapper();
    }


    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        return new ResponseEntity<>(convertToDto(getCurrentUser()), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<UserDto> update(@RequestBody @Valid UserPatchDto userDto) {
        User currentUser = getCurrentUser();
        mapWithSkipNull(userDto, currentUser);
        currentUser.setProfileCompleted(Boolean.TRUE);
        User updated = userService.update(currentUser.getId(), currentUser);

        return new ResponseEntity<>(convertToDto(updated), HttpStatus.OK);
    }

    @PatchMapping("/profile-picture")
    public ResponseEntity<UserDto> changeProfilePicture(@RequestParam("profile-picture") MultipartFile profilePicture) {
        User currentUser = getCurrentUser();
        final String currentProfilePictureURL = currentUser.getProfilePicture();
        if (!(Objects.isNull(currentProfilePictureURL) || currentProfilePictureURL.isEmpty() || currentProfilePictureURL.isBlank())) {
            URI uri = URI.create(currentProfilePictureURL);
            String currentProfilePicture = uri.getPath().substring(1);
            storageService.delete(currentProfilePicture);
        }
        String profilePicturePath = storageService.save(profilePicture);
        currentUser.setProfilePicture(profilePicturePath);
        User updated = userService.update(currentUser.getId(), currentUser);
        return new ResponseEntity<>(convertToDto(updated), HttpStatus.OK);
    }

    @PatchMapping("/reset-password")
    public ResponseEntity<UserDto> resetPassword(@RequestBody @Valid AuthenticatedResetPasswordRequest resetPasswordRequest) {
        User currentUser = getCurrentUser();

        if (userService.checkPassword(currentUser, resetPasswordRequest.getOldPassword())) {
            userService.resetPassword(currentUser, resetPasswordRequest.getNewPassword());
            User updated = userService.update(currentUser.getId(), currentUser);
            return new ResponseEntity<>(convertToDto(updated), HttpStatus.OK);
        }

        throw new UnauthorizedException(new UnauthorizedException(), CoreConstant.Exception.INVALID_PASSWORD, null);

    }

    @PostMapping("/search/trainers")
    public ResponseEntity<List<UserDto>> searchTrainers(@RequestBody SearchDto searchDto) throws BusinessException {
        searchDto.validate();
        Pageable pageable = PageRequest.of(searchDto.getPage(), searchDto.getSize());

        List<User> entities = userService.searchByKeywordAndRole(searchDto.getKeyword(), pageable, GenericEnum.RoleName.TRAINER);
        List<UserDto> dto = entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        long totalTrainers = userService.countByRoles(GenericEnum.RoleName.TRAINER);
        int totalPages = (int) Math.ceil((double) totalTrainers / searchDto.getSize());

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Pages", String.valueOf(totalPages));
        headers.add("Access-Control-Expose-Headers", "X-Total-Pages");
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(dto);
    }


}
