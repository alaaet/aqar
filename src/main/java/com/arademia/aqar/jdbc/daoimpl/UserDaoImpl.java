package com.arademia.aqar.jdbc.daoimpl;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.jdbc.dao.UserDao;
import com.arademia.aqar.model.Authority;
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
import java.util.ArrayList;
import java.util.Arrays;
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
        user.setCreatedAt(LocalDateTime.now());
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(user);
        String sqlQuery = "INSERT INTO "+ ConfigsConst.USERS+" (first_name, last_name, password, email, created_at) VALUES(:firstName, :lastName, :password, :email, :created_at)";
        if(namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1) {
            User usr = getUser(getLastUserId());
        Authority authority = new Authority(usr.getId(), UserConstants.ROLE_USER);
        create(authority);
        usr.setAuthorities(new ArrayList(Arrays.asList(UserConstants.ROLE_USER)));
        return  usr;
        }
        else return null;
    }
    @Override
    public Boolean create(Authority authority) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(authority);
        String sqlQuery = "INSERT INTO "+ ConfigsConst.AUTHORITIES+" (user_id, authority) VALUES(:userId, :value)";
        if(namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1) return true;
        else return false;
    }

    @Override
    public User getUser(Integer id) {
        SqlParameterSource params = new MapSqlParameterSource("ID",id);
        String sqlQuery = "SELECT id, first_name, last_name, password, email, created_at "+
                "FROM "+ ConfigsConst.USERS+" WHERE id = :ID";
        User user = namedParameterJdbcTemplate.queryForObject(sqlQuery, params,new UserRawMapper());
        user.setPassword(null);
        user.setAuthorities(getUserAuthorities(user.getId()));
        return user;
    }

    @Override
    public User getUserByEmail(String email) {
        SqlParameterSource params = new MapSqlParameterSource("EMAIL",email);
        String sqlQuery = "SELECT id, first_name, last_name, password, email, created_at "+
                "FROM "+ ConfigsConst.USERS+" WHERE email = :EMAIL";
        User user = namedParameterJdbcTemplate.queryForObject(sqlQuery, params,new UserRawMapper());
        user.setAuthorities(getUserAuthorities(user.getId()));
        return user;
    }

    @Override
    public List<String> getUserAuthorities(int userId) {
        SqlParameterSource params = new MapSqlParameterSource("USER_ID",userId);
        String sqlQuery = "SELECT * FROM "+ ConfigsConst.AUTHORITIES+" WHERE user_id = :USER_ID";
        List<Authority> authoritiesList = namedParameterJdbcTemplate.query(sqlQuery,params, new AuthorityRawMapper());
        List<String> authorities = new ArrayList<>();
        for(Authority authority : authoritiesList){
            authorities.add(authority.getValue());
        }
        return authorities;
    }

    @Override
    public int getLastUserId() {
        String sqlQuery = "SELECT * FROM "+ ConfigsConst.USERS+" ORDER BY id DESC  LIMIT 1 ";
        return namedParameterJdbcTemplate.query(sqlQuery,new UserRawMapper()).get(0).getId();
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM "+ ConfigsConst.USERS;
        List<User> userList = namedParameterJdbcTemplate.query(sqlQuery, new UserRawMapper());
        for (User user:userList
             ) {
            user.setPassword(null);
            user.setAuthorities(getUserAuthorities(user.getId()));
        }
        return userList;
    }

    @Override
    public boolean delete(User user) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(user);
        String sqlQuery = "DELETE FROM "+ ConfigsConst.USERS+" WHERE id = :id";
        return namedParameterJdbcTemplate.update(sqlQuery, beanParams) == 1;
    }

    @Override
    public boolean update(User user) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(user);
        String sqlQuery = "UPDATE "+ ConfigsConst.USERS+" SET first_name=:firstName, last_name=:lastName, password=:password, email=:email "+"WHERE id = :id";
        Boolean userUpdated = namedParameterJdbcTemplate.update(sqlQuery,beanParams)==1 ;
        Boolean allAuthoritiesUpdated = true;
        for(String authority : user.getAuthorities()){
            if (!update(new Authority(user.getId(),authority))) allAuthoritiesUpdated = false;
        }
        if (userUpdated && allAuthoritiesUpdated) return  true;
        else return false;
    }
    @Override
    public boolean update(Authority authority) {
        SqlParameterSource beanParams = new BeanPropertySqlParameterSource(authority);
        String sqlQuery = "UPDATE "+ ConfigsConst.AUTHORITIES+" SET authority=:value "+"WHERE user_id=:userId";
        return namedParameterJdbcTemplate.update(sqlQuery,beanParams)==1;
    }
}
