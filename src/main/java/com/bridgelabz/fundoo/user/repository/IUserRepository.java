package com.bridgelabz.fundoo.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoo.user.model.User;
 
@Repository
public interface IUserRepository extends MongoRepository<User, String>{

	public Optional<User> findByEmail(String email);
	
}
