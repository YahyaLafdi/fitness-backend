package com.metamafitness.fitnessbackend.service;

import com.metamafitness.fitnessbackend.model.Blog;
import com.metamafitness.fitnessbackend.model.GenericEnum;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService extends GenericService<Blog> {
    List<Blog> findByCreator(Long id, int page, int size);
    long countByCreator(Long currentUserId);

    List<Blog> searchWithState(String keyword, GenericEnum.BlogState submitted, Pageable pageable);
}
