package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User,Integer> {

    @Query("SELECT * FROM "+ ConfigsConst.USERS+" u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);
    @Query("SELECT * FROM "+ ConfigsConst.USERS+" u WHERE u.username = :username")
    User getUserByUsername(@Param("username") String username);
}
