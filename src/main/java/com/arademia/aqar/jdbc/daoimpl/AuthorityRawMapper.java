package com.arademia.aqar.jdbc.daoimpl;

import com.arademia.aqar.model.Authority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class AuthorityRawMapper implements RowMapper<Authority> {
    public Authority mapRow(ResultSet rs, int rowNum) throws SQLException {
        Authority authority = new Authority();
        authority.setUserId(rs.getInt("user_id"));
        authority.setValue(rs.getString("authority"));
        return authority;
    }
}