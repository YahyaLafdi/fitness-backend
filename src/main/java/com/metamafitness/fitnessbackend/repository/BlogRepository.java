package com.metamafitness.fitnessbackend.repository;

import com.metamafitness.fitnessbackend.common.CoreConstant;
import com.metamafitness.fitnessbackend.model.Blog;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public interface BlogRepository extends GenericRepository<Blog>{

    default List<Blog> searchByState(GenericEnum.BlogState state, String keyword, Pageable pageable) {
        Specification<Blog> spec = Specification.where(hasKeyword(keyword))
                .and((root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    predicates.add(cb.equal(root.get("state"), state));
                    return cb.and(predicates.toArray(new Predicate[0]));
                });
        if (pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(CoreConstant.Pagination.DEFAULT_SORT_PROPERTY).descending());
        }
        return findAll(spec, pageable).getContent();
    }

    List<Blog> findByCreatedBy_id(Long id, Pageable pageable);
    long countByCreatedBy_id(Long id);
}
