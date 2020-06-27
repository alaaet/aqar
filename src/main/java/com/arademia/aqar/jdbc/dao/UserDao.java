package com.arademia.aqar.jdbc.dao;

import com.arademia.aqar.model.User;

import javax.sql.DataSource;
import java.util.List;

public interface UserDao {

    // Set the data-source that will be required to create a connection to the database
    public void setDataSource(DataSource ds);

    // Create a record in the User table
    public int create(User user);

    // Retrieve a single User
    public User getUser(Integer id);

    // Retrieve the ID of the last User
    public int getLastUserId();

    // Retrieve all Users from the table
    public List<User> getAllUsers();

    // Delete a specific User from the table.
    public boolean delete(User node);

    // Update an existing User
    public boolean update(User node);
}
