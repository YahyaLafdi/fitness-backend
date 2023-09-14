package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import com.metamafitness.fitnessbackend.model.Program;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface ProgramRepository extends GenericRepository<Program>{

    default List<Program> findByCategoryAndState(String category, String state, String keyword, Pageable pageable) {
        Specification<Program> spec = Specification.where(hasKeyword(keyword))
                .and((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (!category.isEmpty()) {
                        GenericEnum.ProgramCategory categoryEnum = GenericEnum.ProgramCategory.valueOf(category.toUpperCase());
                        predicates.add(cb.equal(root.get("category"), categoryEnum));
                    }
                    if(!state.isEmpty()){
                        GenericEnum.ProgramState stateEnum = GenericEnum.ProgramState.valueOf(state.toUpperCase());
                        predicates.add(cb.equal(root.get("state"), stateEnum));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                });
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(CoreConstant.Pagination.DEFAULT_SORT_PROPERTY).descending());
        }
        return findAll(spec, pageable).getContent();
    }
    List<Program> findByCreatedBy_id(Long id, Pageable pageable);

    List<Program> findByEnrollments_user_id(Long id, Pageable pageable);
    Long countByEnrollments_user_id(Long id);

    long countByCreatedBy_id(Long id);
    List<Program> findByCategory(GenericEnum.ProgramCategory category, Pageable pageable);
    long countByCategory(GenericEnum.ProgramCategory category);


}
