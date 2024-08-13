package com.semicolon.data.repositories;

import com.semicolon.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByEmail (String email);
    List<User> findByEmail(String email);
    User findByUsername(String username);
}
