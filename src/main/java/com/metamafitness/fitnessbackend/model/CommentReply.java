package com.metamafitness.fitnessbackend.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CommentReply extends GenericEntity {

    private String reply;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private SectionComment comment;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "created_by_id")
    private User createdBy;

}