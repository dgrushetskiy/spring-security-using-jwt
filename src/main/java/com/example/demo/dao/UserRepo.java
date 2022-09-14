package com.example.demo.dao;

import com.example.demo.model.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
