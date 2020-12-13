package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.*;
import com.arademia.aqar.entity.Alert;
import com.arademia.aqar.entity.QrCode;
import com.arademia.aqar.entity.User;
import com.arademia.aqar.entity.util.ModelConverter;
import com.arademia.aqar.repos.AlertRepository;
import com.arademia.aqar.repos.UserRepository;
import com.arademia.aqar.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Mappings.ALERTS)
@CrossOrigin(origins = {"http://localhost:9000","http://167.86.81.129:8082","http://reftag.net","https://clavitag.com","https://www.clavitag.com"})
public class AlertController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AlertRepository alertRepo;
    @Autowired
    private ModelConverter converter;

    /*
    * METHODS
    * */
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createAlert(@RequestBody NewOrUpdateAlertRequest rawAlert, HttpServletRequest request)  {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepo.getUserByEmail(email);
        Alert alert = converter.toAlert(rawAlert);
        alert.setUserId(usr.getId());
        try{
        final Alert createdAlert = alertRepo.save(alert);
        if (createdAlert == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Alert was not saved, please contact us"));
        } else {
            return ResponseEntity.ok(new ResponseMessage("Alert was created successfully!"));
        }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> updateAlert(@PathVariable("id") int id, @RequestBody NewOrUpdateAlertRequest rawAlert) throws URISyntaxException{
        Alert processedAlert = converter.toAlert(rawAlert);
        Alert alert =  alertRepo.findById(id).get();
        alert.setTitle(processedAlert.getTitle());
        alert.setCompensation(processedAlert.getCompensation());
        alert.setBody(processedAlert.getBody());
        alert.setTags(processedAlert.getTags());
        alert.setUpdatedAt(LocalDateTime.now());
        final Alert updatedAlert = alertRepo.save(alert);
        if (updatedAlert!= null) return ResponseEntity.ok(new ResponseMessage("Alert was updated successfully!"));
        else return ResponseEntity.badRequest().body(new ResponseMessage("Update operation failed, please contact us"));
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlert(@PathVariable("id") int id) throws URISyntaxException {
        Alert deletedAlert = alertRepo.findById(id).get();
        deletedAlert.setDeletedAt(LocalDateTime.now());
        long initCount = alertRepo.count();
        alertRepo.save(deletedAlert);
        if (initCount<= alertRepo.count())
        {
            return ResponseEntity.badRequest().body(new ResponseMessage("Alert was not deleted, please contact us"));
        }
        else
        {
            return ResponseEntity.ok(new ResponseMessage("Alert was deleted successfully!"));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping(value = Mappings.BY_USER)
    public ResponseEntity<?> getUserAlerts(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepo.getUserByEmail(email);
        List<Alert> alerts = alertRepo.getAlertsByUserId(usr.getId());
        List<GetAlertResponse> processedAlerts = new ArrayList<>();
        alerts.forEach(alert ->{ processedAlerts.add(new GetAlertResponse(alert));});
        return  ResponseEntity.ok(processedAlerts);
    }

    /*
    * ADMIN ONLY
    * */

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAlerts() {
        List<Alert> alerts = (List<Alert>)alertRepo.findAll();
        List<GetAlertResponse> processedAlerts = new ArrayList<>();
        alerts.forEach(alert ->{ processedAlerts.add(new GetAlertResponse(alert));});
        return  ResponseEntity.ok(processedAlerts);
    }

}
