package com.lifelike.dev.springmongodbtodo.repository;

import com.lifelike.dev.springmongodbtodo.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
}
