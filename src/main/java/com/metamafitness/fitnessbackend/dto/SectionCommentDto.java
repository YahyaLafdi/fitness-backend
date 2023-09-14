package com.metamafitness.fitnessbackend.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectionCommentDto extends GenericDto{
    private String comment;

    private UserDto createdBy;

    private List<CommentReplyDto> replies ;

}
