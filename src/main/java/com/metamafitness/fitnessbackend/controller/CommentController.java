package com.metamafitness.fitnessbackend.controller;

import com.metamafitness.fitnessbackend.dto.CommentReplyDto;
import com.metamafitness.fitnessbackend.dto.SectionCommentDto;
import com.metamafitness.fitnessbackend.exception.ResourceOwnershipException;
import com.metamafitness.fitnessbackend.model.CommentReply;
import com.metamafitness.fitnessbackend.model.ProgramSection;
import com.metamafitness.fitnessbackend.model.SectionComment;
import com.metamafitness.fitnessbackend.model.User;
import com.metamafitness.fitnessbackend.service.CommentService;
import com.metamafitness.fitnessbackend.service.ProgramSectionService;
import com.metamafitness.fitnessbackend.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.metamafitness.fitnessbackend.common.CoreConstant.Exception.AUTHORIZATION_RESOURCE_OWNERSHIP;


@RestController
@RequestMapping("/api/comments")
public class CommentController extends GenericController<SectionComment, SectionCommentDto>{
    private final ProgramSectionService programSectionService;

    private final CommentService commentService;

    private final ReplyService replyService;

    public CommentController(ProgramSectionService programSectionService, CommentService commentService, ReplyService replyService) {
        this.programSectionService = programSectionService;
        this.commentService = commentService;
        this.replyService = replyService;
    }

    @PostMapping("/{commentId}/reply")
    public ResponseEntity<CommentReplyDto> reply(@PathVariable Long commentId, @RequestParam("reply") String reply) {
        SectionComment comment = commentService.findById(commentId);
        User currentUser = getCurrentUser();

        CommentReply commentReply = CommentReply.builder()
                .comment(comment)
                .createdBy(currentUser)
                .reply(reply)
                .build();
        CommentReply saved = replyService.save(commentReply);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(getModelMapper().map(saved, CommentReplyDto.class));
    }

    @DeleteMapping("/reply/{idReply}")
    public ResponseEntity<Boolean> deleteReply(@PathVariable Long idReply) {
        CommentReply reply = replyService.findById(idReply);
        if(isNotOwner(reply)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }

        Boolean deleted = replyService.delete(idReply);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(deleted);
    }

    @PatchMapping("/reply/{idReply}")
    public ResponseEntity<CommentReplyDto> deleteReply(@PathVariable Long idReply, @RequestParam("reply") String newReply) {
        CommentReply reply = replyService.findById(idReply);
        if(isNotOwner(reply)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }
        reply.setReply(newReply);
        CommentReply updated = replyService.patch(reply);
        return ResponseEntity.status(HttpStatus.OK)
                .body(getModelMapper().map(updated, CommentReplyDto.class));
    }

    @PostMapping("/{sectionId}")
    public ResponseEntity<SectionCommentDto> addComment(@PathVariable("sectionId") Long sectionId, @RequestParam String comment) {
        final User currentUser = getCurrentUser();
        ProgramSection section = programSectionService.findById(sectionId);

        SectionComment sectionComment = SectionComment.builder()
                .section(section)
                .comment(comment)
                .createdBy(currentUser)
                .build();


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertToDto(commentService.save(sectionComment)));

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Boolean> delete(@PathVariable("commentId") Long commentId) {
        SectionComment comment = commentService.findById(commentId);
        if(isNotOwner(comment)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }

        Boolean deleted = commentService.delete(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(deleted);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<SectionCommentDto> update(@PathVariable("commentId") Long commentId,
                                                    @RequestParam String newComment){
        SectionComment comment = commentService.findById(commentId);
        if(isNotOwner(comment)) {
            throw new ResourceOwnershipException(new ResourceOwnershipException(), AUTHORIZATION_RESOURCE_OWNERSHIP, null);
        }
        comment.setComment(newComment);
        SectionComment updated = commentService.patch(comment);

        return ResponseEntity.status(HttpStatus.OK).body(convertToDto(updated));
    }
    private boolean isNotOwner(SectionComment comment) {
        final Long currentUserId = getCurrentUserId();
        final Long resourceOwnerId = comment.getCreatedBy().getId();
        return !currentUserId.equals(resourceOwnerId);
    }

    private boolean isNotOwner(CommentReply reply) {
        final Long currentUserId = getCurrentUserId();
        final Long resourceOwnerId = reply.getCreatedBy().getId();
        return !currentUserId.equals(resourceOwnerId);
    }

}
