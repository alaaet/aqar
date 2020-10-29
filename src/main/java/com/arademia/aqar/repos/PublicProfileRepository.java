package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.PublicProfile;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublicProfileRepository extends CrudRepository<PublicProfile,Integer> {
    @Query("SELECT * FROM "+ ConfigsConst.PUBLIC_PROFILES+" u WHERE u.user_id = :user_id")
    PublicProfile getPublicProfileByUserId(@Param("user_id") Integer userId);
}
