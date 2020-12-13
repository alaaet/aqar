package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.Alert;
import com.arademia.aqar.entity.QrCode;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlertRepository extends CrudRepository<Alert,Integer> {

    @Query("SELECT * FROM "+ ConfigsConst.ALERTS+" u WHERE u.user_id = :user_id")
    List<Alert> getAlertsByUserId(@Param("user_id") Integer userId);
}
