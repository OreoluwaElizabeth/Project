package com.semicolon.data.repositories;

import com.semicolon.data.models.TodoTasks;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends MongoRepository<TodoTasks, String> {
    List<TodoTasks> findByTitle(String title);
}
