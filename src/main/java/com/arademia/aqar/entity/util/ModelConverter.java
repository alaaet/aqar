package com.arademia.aqar.entity.util;

import com.arademia.aqar.config.model.NewOrUpdateTagRequest;
import com.arademia.aqar.config.model.NewOrUpdateUserRequest;
import com.arademia.aqar.entity.QrCode;
import com.arademia.aqar.entity.User;
import com.arademia.aqar.entity.constants.UserConstants;
import com.arademia.aqar.repos.DimensionTypeRepository;
import com.arademia.aqar.repos.MaterialTypeRepository;
import com.arademia.aqar.repos.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;


@Component
@Scope(value = "singleton")
public class ModelConverter {
    private static ModelConverter single_instance = null;
    @Autowired
    private MaterialTypeRepository materialTypeRepo;
    @Autowired
    private DimensionTypeRepository dimensionTypeRepo;
    @Autowired
    private TagRepository tagRepo;

    public User toUser(NewOrUpdateUserRequest rowUser){
        User usr = new User();
        usr.setTitle(UserConstants.Title.valueOf(rowUser.getTitle()));
        usr.setFirstName(rowUser.getFirstName());
        usr.setLastName(rowUser.getLastName());
        usr.setEmail(rowUser.getEmail());
        usr.setRole(UserConstants.Role.valueOf(rowUser.getRole()));
        usr.setBirthday(LocalDateTime.now());
        return usr;
    }

    public QrCode toTag(NewOrUpdateTagRequest rowTag){
        QrCode tag = new QrCode();
        tag.setValue(rowTag.getValue());
        tag.setMaterialType(materialTypeRepo.findById(rowTag.getMaterial_id()).get());
        tag.setDimensionType(dimensionTypeRepo.findById(rowTag.getDimension_id()).get());
        return tag;
    }


}
