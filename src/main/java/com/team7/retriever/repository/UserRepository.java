package com.team7.retriever.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.team7.retriever.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	Optional<User> findByEmployeeId(String employeeId);

	boolean existsByEmployeeId(String employeeId);
}
