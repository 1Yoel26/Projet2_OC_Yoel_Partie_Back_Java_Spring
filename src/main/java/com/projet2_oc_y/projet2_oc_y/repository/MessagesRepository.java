package com.projet2_oc_y.projet2_oc_y.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet2_oc_y.projet2_oc_y.model.Messages;

@Repository
public interface MessagesRepository extends CrudRepository<Messages, Long> {

}
