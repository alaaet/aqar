package com.arademia.aqar.repos;

import com.arademia.aqar.config.ConfigsConst;
import com.arademia.aqar.entity.Comment;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment,Integer> {

    @Query("SELECT * FROM "+ ConfigsConst.COMMENTS+" u WHERE u.qr_code_id = :qr_code_id")
    List<Comment> getCommentsByQrCodeId(@Param("qr_code_id") Integer qrCodeId);
}
