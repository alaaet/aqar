package com.arademia.aqar.controller;

import com.arademia.aqar.config.model.*;
import com.arademia.aqar.entity.DimensionType;
import com.arademia.aqar.entity.MaterialType;
import com.arademia.aqar.entity.QrCode;
import com.arademia.aqar.entity.User;
import com.arademia.aqar.entity.util.ModelConverter;
import com.arademia.aqar.repos.DimensionTypeRepository;
import com.arademia.aqar.repos.MaterialTypeRepository;
import com.arademia.aqar.repos.TagRepository;
import com.arademia.aqar.repos.UserRepository;
import com.arademia.aqar.util.Mappings;
import com.arademia.aqar.util.SupportingTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.arademia.aqar.util.SupportingTools;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = Mappings.TAGS)
@CrossOrigin(origins = {"http://localhost:9000","http://167.86.81.129:8082","http://reftag.net"})
public class TagController {

    private static SupportingTools util = new SupportingTools();
    @Autowired
    private TagRepository tagRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private DimensionTypeRepository dimRepo;
    @Autowired
    private MaterialTypeRepository matRepo;
    @Autowired
    private ModelConverter converter;

    /*
    * Methods
    * */


    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @PostMapping(value = Mappings.ACTIVATE)
    public ResponseEntity<?> activateTag(@RequestBody ActivationRequest activationRequest,HttpServletRequest request) throws URISyntaxException{
        // get the current user
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepo.getUserByEmail(email);
        // change the state of the tag to activated
        List<QrCode> tags = tagRepo.getTagsByValue(activationRequest.getTagCode());
        if(tags.size()>0)
        {
            QrCode tag = tags.get(0);
            if(tag.getAssignedAt()==null)
            {
                if (tag.getActivationCode().equals(activationRequest.getActCode())){
                tag.setAssignedAt(LocalDateTime.now());
                tag.setUserId(usr.getId());
                final QrCode updatedTag = tagRepo.save(tag);
                if (updatedTag!= null) return ResponseEntity.ok(updatedTag);
                else return ResponseEntity.badRequest().body(new ResponseMessage("Update operation failed, please contact us"));
                }else
                    return ResponseEntity.badRequest().body(new ResponseMessage("The activation code is incorrect!"));
            }else{
                return ResponseEntity.badRequest().body(new ResponseMessage("This tag was activated previously by another user!"));
            }
        }
        else
            return ResponseEntity.badRequest().body(new ResponseMessage("We could not find this tag,please contact us"));
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getTag(@PathVariable("id") int id) {
        final Optional<QrCode> tag = tagRepo.findById(id);
        if (tag == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("We could not fetch this tag,please contact us"));
        } else {
            return ResponseEntity.ok(tag);
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") int id) throws URISyntaxException {
        QrCode deletedTag = tagRepo.findById(id).get();
        deletedTag.setDeletedAt(LocalDateTime.now());
        long initCount = tagRepo.count();
        tagRepo.save(deletedTag);
        if (initCount<= tagRepo.count())
        {
            return ResponseEntity.badRequest().body(new ResponseMessage("Tag was not deleted, please contact us"));
        }
        else
        {
            return ResponseEntity.ok(new ResponseMessage("Tag was deleted successfully!"));
        }
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping(value = Mappings.BY_USER)
    public ResponseEntity<List<QrCode>> getUserTags(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        String email = principal.getName();
        User usr = userRepo.getUserByEmail(email);
        List<QrCode> tags = tagRepo.getTagsByUserId(usr.getId());
        return  ResponseEntity.ok(tags);
    }


    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> updateTag(@PathVariable("id") int id, @RequestBody NewOrUpdateTagRequest rowTag) throws URISyntaxException{
        QrCode tag =  tagRepo.findById(id).get();
        tag.setValue(rowTag.getValue());
        tag.setDimensionType(dimRepo.findById(rowTag.getDimension_id()).get());
        tag.setMaterialType(matRepo.findById(rowTag.getMaterial_id()).get());
        if (rowTag.isAssigned()) tag.setAssignedAt(LocalDateTime.now());
        if (rowTag.isLost()) tag.setLostAt(LocalDateTime.now());
        final QrCode updatedTag = tagRepo.save(tag);
        if (updatedTag!= null) return ResponseEntity.ok(updatedTag);
        else return ResponseEntity.badRequest().body(new ResponseMessage("Update operation failed, please contact us"));
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping(Mappings.MATERIAL_TYPES)
    public ResponseEntity<List<MaterialType>> getTagMaterialTypes() {
        List<MaterialType> types = (List<MaterialType>)matRepo.findAll();
        return  ResponseEntity.ok(types);
    }

    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @GetMapping(Mappings.DIMENSION_TYPES)
    public ResponseEntity<List<DimensionType>> getTagDimensionTypes() {
        List<DimensionType> types = (List<DimensionType>)dimRepo.findAll();
        return  ResponseEntity.ok(types);
    }
    //////////////////// ADMIN METHODS ////////////////////



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody NewOrUpdateTagRequest rowTag) throws URISyntaxException{
        QrCode tag = converter.toTag(rowTag);
        tag.setActivationCode(generateUniqueString(6));
        final QrCode createdTag = tagRepo.save(tag);
        if (createdTag == null) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Tag was not saved, please contact us"));
        } else {
            URI uri = new URI(ServletUriComponentsBuilder.fromUri(new URI(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()))
                    .path(Mappings.TAGS).queryParam("id",createdTag.getId()).toUriString());
            return ResponseEntity.created(uri)
                    .body(createdTag);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(Mappings.GENERATE_ACTIVATION_CODE)
    public ResponseEntity<String> generateActivationCode() {
        return  ResponseEntity.ok(generateUniqueString(6));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<QrCode>> getTags() {
        List<QrCode> tags = (List<QrCode>)tagRepo.findAll();
        return  ResponseEntity.ok(tags);
    }






// HELPER METHODS
    private String generateUniqueString(int size){
        String activationCode = util.generateString(size);
        List<QrCode> tagsWithTheSameActivationCode = tagRepo.getTagByActivationCode(activationCode);
        if (tagsWithTheSameActivationCode.size()>0) return generateUniqueString(size);
        else return activationCode;
    }





}
