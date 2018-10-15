package com.expense.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.expense.model.Group;
/**
 * Group db queries
 * @author USER
 *
 */
public interface GroupRepository extends MongoRepository<Group, String> {

}
