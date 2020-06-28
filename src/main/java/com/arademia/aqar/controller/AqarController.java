package com.arademia.aqar.controller;

import com.arademia.aqar.jdbc.dao.UserDao;
import com.arademia.aqar.model.User;
import com.arademia.aqar.util.Mappings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
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
    @RequestMapping(value=Mappings.GET_USER,method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@RequestParam int id) {
        User user= userDao.getUser(id);
        return user;
    }

    @RequestMapping(value=Mappings.ADD_USER,method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user, HttpServletRequest request) throws URISyntaxException {
        User createdUser = userDao.create(user);

        if (createdUser == null) {
            return ResponseEntity.notFound().build();
        } else {

            URI uri = new URI(ServletUriComponentsBuilder.fromUri(new URI(ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString()))
                    .path("/"+Mappings.GET_USER).queryParam("id",createdUser.getId()).toUriString());
            return ResponseEntity.created(uri)
                    .body(createdUser);
        }
    }

    @RequestMapping(value=Mappings.UPDATE_USER,method = RequestMethod.POST)
    @ResponseBody
    public boolean updateUser(@RequestBody User user, HttpServletRequest request) throws URISyntaxException {
        return userDao.update(user);
    }

    @RequestMapping(value=Mappings.DELETE_USER,method = RequestMethod.DELETE)
    @ResponseBody
    public boolean deleteUser(@RequestParam int id, HttpServletRequest request) throws URISyntaxException {
        return userDao.delete(userDao.getUser(id));
    }
}
