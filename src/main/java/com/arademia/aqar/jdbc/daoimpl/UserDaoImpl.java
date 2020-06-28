package com.arademia.aqar.jdbc.daoimpl;

import com.arademia.aqar.config.Configs;
import com.arademia.aqar.jdbc.dao.UserDao;
import com.arademia.aqar.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
public class UserDaoImpl implements UserDao {
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource ds) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(ds);
    }

    @Override
    public User create(User user) {
        user.setCreated_at(LocalDateTime.now());
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(user);
        String sqlQuery = "INSERT INTO "+ Configs.USERS+" (first_name, last_name, password, email, created_at) VALUES(:firstName, :lastName, :password, :email, :created_at)";
        if(namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1) return getUser(getLastUserId());
        else return new User();
    }

    @Override
    public User getUser(Integer id) {
        SqlParameterSource params = new MapSqlParameterSource("ID",id);
        String sqlQuery = "SELECT id, first_name, last_name, password, email, created_at "+
                "FROM "+Configs.USERS+" WHERE id = :ID";
        User user = namedParameterJdbcTemplate.queryForObject(sqlQuery, params,new UserRawMapper());
        user.setPassword(null);
        return user;
    }

    @Override
    public int getLastUserId() {
        String sqlQuery = "SELECT * FROM "+Configs.USERS+" ORDER BY id DESC  LIMIT 1 ";
        return namedParameterJdbcTemplate.query(sqlQuery,new UserRawMapper()).get(0).getId();
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM "+Configs.USERS;
        List<User> userList = namedParameterJdbcTemplate.query(sqlQuery, new UserRawMapper());
        for (User user:userList
             ) {
            user.setPassword(null);
        }
        return userList;
    }

    @Override
    public boolean delete(User user) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(user);
        String sqlQuery = "DELETE FROM "+Configs.USERS+" WHERE id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1;
    }

    @Override
    public boolean update(User user) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(user);
        String sqlQuery = "UPDATE "+Configs.USERS+" SET first_name=:firstName, last_name=:lastName, password=:password, email=:email "+"WHERE id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery,beanParams)==1;
    }
}
