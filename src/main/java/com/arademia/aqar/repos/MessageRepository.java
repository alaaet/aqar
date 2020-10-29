package com.arademia.aqar.repos;

import com.arademia.aqar.entity.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message,Integer> {
}
