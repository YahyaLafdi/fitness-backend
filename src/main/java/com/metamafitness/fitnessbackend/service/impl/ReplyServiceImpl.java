package com.metamafitness.fitnessbackend.service.impl;

import com.metamafitness.fitnessbackend.model.CommentReply;
import com.metamafitness.fitnessbackend.repository.GenericRepository;
import com.metamafitness.fitnessbackend.service.ReplyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl extends GenericServiceImpl<CommentReply> implements ReplyService {
    public ReplyServiceImpl(GenericRepository<CommentReply> genericRepository, ModelMapper modelMapper) {
        super(genericRepository, modelMapper);
    }
}
