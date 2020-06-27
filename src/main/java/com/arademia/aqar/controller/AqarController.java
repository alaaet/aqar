package com.arademia.aqar.controller;

import com.arademia.aqar.jdbc.dao.UserDao;
import com.arademia.aqar.model.User;
import com.arademia.aqar.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class AqarController {
    @Autowired
    private UserDao userDao;

    /////////////////////////////////////////// methods ///////////////////////////////////////////
    @RequestMapping(value=Mappings.LIST_USERS,method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() {
        List<User> users= userDao.getAllUsers();
        return users;
    }
}
