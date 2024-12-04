package com.myproject.myspringproject.repository;


import com.myproject.myspringproject.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, ObjectId> {
    User findByUsername(String username);
}
