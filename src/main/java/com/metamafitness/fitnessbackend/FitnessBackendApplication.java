package com.metamafitness.fitnessbackend;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.exception.ElementAlreadyExistException;
import com.metamafitness.fitnessbackend.model.AdminRole;
import com.metamafitness.fitnessbackend.model.DevRole;
import com.metamafitness.fitnessbackend.model.TrainerRole;
import com.metamafitness.fitnessbackend.model.UserRole;
import com.metamafitness.fitnessbackend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FitnessBackendApplication implements CommandLineRunner {

    private final UserRoleService userRoleService;

    private final TrainerRoleService trainerRoleService;

    private final AdminRoleService adminRoleService;

    private final DevRoleService devRoleService;



    private final UserService userService;

    public FitnessBackendApplication(UserRoleService userRoleService, TrainerRoleService trainerRoleService, AdminRoleService adminRoleService, UserService userService,
                                     DevRoleService devRoleService) {
        this.userRoleService = userRoleService;
        this.trainerRoleService = trainerRoleService;
        this.adminRoleService = adminRoleService;
        this.userService = userService;
        this.devRoleService = devRoleService;

    }

    public static void main(String[] args) {

        SpringApplication.run(FitnessBackendApplication.class, args);
    }


    @Override
    public void run(String... args) {
        // generate roles
        userRoleService.save(new UserRole());
        trainerRoleService.save(new TrainerRole());
        adminRoleService.save(new AdminRole());
        devRoleService.save(new DevRole());

        // generate admin account
        try{
            userService.saveAdmin();
            userService.saveDev();
            userService.saveTrainer();
            log.info(CoreConstant.Success.USER_ROLE_CREATED_SUCCESSFULLY);
        }
        catch (ElementAlreadyExistException ex) {
            log.info(CoreConstant.Exception.ADMIN_ACCOUNT_ALREADY_CREATED);
        }


    }
}
