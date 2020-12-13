package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.*;
import com.arademia.aqar.entity.Comment;
import com.arademia.aqar.entity.QrCode;
import com.arademia.aqar.entity.User;
import com.arademia.aqar.entity.util.ModelConverter;
import com.arademia.aqar.repos.CommentRepository;
import com.arademia.aqar.repos.TagRepository;
import com.arademia.aqar.repos.UserRepository;
import com.arademia.aqar.util.MailSenderUtil;
import com.arademia.aqar.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = Mappings.COMMENTS)
@CrossOrigin(origins = {"http://localhost:9000","http://167.86.81.129:8082","http://reftag.net","https://clavitag.com","https://www.clavitag.com"})
public class CommentsController {

    @Autowired
    UserRepository userRepo;
    @Autowired
    TagRepository tagRepo;
    @Autowired
    CommentRepository commentRepo;
    @Autowired
    private ModelConverter converter;

    @PostMapping
    public ResponseEntity<?> sendComment(@RequestBody NewCommentRequest request) throws URISyntaxException {
        try {
            Comment newComment = converter.toComment(request);
            final Comment createdComment = commentRepo.save(newComment);
            if (createdComment == null) {
                return ResponseEntity.badRequest().body(new ResponseMessage("Your comment was not saved, please contact us"));
            } else {
                return ResponseEntity.ok(new ResponseMessage("Comment was sent successfully!"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ResponseMessage("An error occurred while adding your comment, please contact our support team!"));
        }
    }

    @GetMapping(Mappings.BY_TAG_CODE+"/{tagCode}")
    public ResponseEntity<?> getCommentsByTagCode(@PathVariable("tagCode") String tagCode) {
        QrCode tag = tagRepo.getTagsByValue(tagCode).get(0);
        List<Comment> comments = commentRepo.getCommentsByQrCodeId(tag.getId());
        if(comments.size()>0) return  ResponseEntity.ok(comments);
        else return ResponseEntity.ok().body(new ResponseMessage("There are no comments yet."));
    }

}
