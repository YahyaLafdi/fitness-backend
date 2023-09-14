package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.dto.JwtToken;
import com.metamafitness.fitnessbackend.exception.BusinessException;
import com.metamafitness.fitnessbackend.exception.ElementNotFoundException;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends GenericService<User> {

    List<User> findNewEnrolledUsers(Long trainerId, int page, int size) throws BusinessException;

    Long countNewEnrolledUsers(Long trainerId);

    User findByEmail(String email) throws ElementNotFoundException;

    User findByEmail_v2(String email);
    List<User> searchByKeywordAndRole(String keyword, Pageable pageable, GenericEnum.RoleName roleName);
    long countByRoles(GenericEnum.RoleName roleName);


    boolean sendVerificationEmail(User user);

    void generateVerificationCode(User user);

    User verify(String verificationCode);

    void saveAdmin();

    void saveTrainer();

    void saveDev();

    JwtToken generateResetPasswordToken(User user);

    void resetPassword(User user, String newPassword);

    boolean checkPassword(User user, String password);
}
