package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.AuthProvider;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthProviderRepository extends CrudRepository<AuthProvider,Integer> {
    @Query("SELECT * FROM "+ ConfigsConst.AUTH_PROVIDERS+" u WHERE u.social_id = :social_id")
    AuthProvider getSocialAccountBySocialId(@Param("social_id") String socialId);
}
