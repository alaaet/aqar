package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.NewEmailRequest;
import com.arademia.aqar.config.model.ResponseMessage;
import com.arademia.aqar.entity.User;
import com.arademia.aqar.repos.UserRepository;
import com.arademia.aqar.util.MailSenderUtil;
import com.arademia.aqar.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping(value = Mappings.MAIL)
@CrossOrigin(origins = {"http://localhost:9000","http://reftag.net","https://clavitag.com","https://www.clavitag.com","http://clavitag.com","http://www.clavitag.com"})
public class MailController {

    @Autowired
    MailSenderUtil senderUtil ;
    @Autowired
    UserRepository userRepo;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> sendAnEmail(@RequestBody NewEmailRequest data) throws URISyntaxException {

        try {
            User usr = userRepo.getUserByEmail(data.getRecipient());
            String userFirstName =  usr.getFirstName().substring(0,1).toUpperCase()+usr.getFirstName().substring(1);
            senderUtil.sendEmail(data.getRecipient(),userFirstName,data.getSubject(), data.getBody());
            return ResponseEntity.ok(new ResponseMessage("Email was sent successfully!"));
        } catch (MailException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ResponseMessage("There was an error with the email service!"));
        } catch (Exception exp){
            exp.printStackTrace();
            return ResponseEntity.badRequest().body(new ResponseMessage("There was an error with the email service!"));
        }
    }

}
