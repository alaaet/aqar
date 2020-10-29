package com.arademia.aqar.repos;

import com.arademia.aqar.entity.Conversation;
import org.springframework.data.repository.CrudRepository;

public interface ConversationRepository extends CrudRepository<Conversation,Integer> {
}
