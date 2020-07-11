package com.arademia.aqar.jdbc.daoimpl;

import com.arademia.aqar.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
public class UserRawMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setCreatedAt(convertToLocalDateTime(rs.getTimestamp("created_at")));

        return user;
    }

    private LocalDateTime convertToLocalDateTime(Timestamp timestamp){
        if(timestamp!=null){
            //log.info("Timestamp has been converted to: {}",timestamp.toLocalDateTime());
            return timestamp.toLocalDateTime();}
        else{
            return null;
        }
    }
}
