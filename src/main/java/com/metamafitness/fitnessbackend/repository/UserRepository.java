package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.model.AppUserRole;
import com.metamafitness.fitnessbackend.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.List;
import java.util.Optional;

import static com.metamafitness.fitnessbackend.model.GenericEnum.RoleName;

public interface UserRepository extends GenericRepository<User>{

    List<User> findDistinctByEnrollments_program_createdBy_id(Long trainer_Id, Pageable pageable);

    Long countDistinctByEnrollments_program_createdBy_id(Long trainer_Id);
    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(String verificationCode);
    long countByRoles_Name(RoleName roleName);
    default List<User> searchByKeywordAndRole(String keyword, Pageable pageable,RoleName roleName) {
        Specification<User> roleSpec = (root, query, criteriaBuilder) -> {
            Join<User, AppUserRole> rolesJoin = root.join("roles", JoinType.INNER);
            return criteriaBuilder.equal(rolesJoin.get("name"), roleName);
        };
        Specification<User> keywordSpec = Specification.where(hasKeyword(keyword));
        return findAll(Specification.where(roleSpec).and(keywordSpec), pageable).toList();
    }

}
