package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.QrCode;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends CrudRepository<QrCode,Integer> {
    @Query("SELECT * FROM "+ ConfigsConst.QR_CODES+" u WHERE u.user_id = :user_id")
    List<QrCode> getTagsByUserId(@Param("user_id") Integer userId);

    @Query("SELECT * FROM "+ ConfigsConst.QR_CODES+" u WHERE u.value = :value")
    List<QrCode> getTagsByValue(@Param("value") String value);

    @Query("SELECT * FROM "+ ConfigsConst.QR_CODES+" u WHERE u.activation_code = :activation_code")
    List<QrCode> getTagByActivationCode(@Param("activation_code") String activationCode);
}
