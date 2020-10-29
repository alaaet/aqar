package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.ContactDetail;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactDetailRepository extends CrudRepository<ContactDetail,Integer> {
    @Query("SELECT * FROM "+ ConfigsConst.CONTACT_DETAILS+" u WHERE u.pp_id = :p_id")
    List<ContactDetail> getContactDetailsByPublicProfileId(@Param("pp_id") Integer ppId);
}
