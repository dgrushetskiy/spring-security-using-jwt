package com.example.demo.controller;

import com.example.demo.dao.UserRepo;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepo repo;

    @GetMapping("/users")
    private List<User> getAllUsers(){
        return (List<User>) repo.findAll();
    }

    @GetMapping("/user/{id}")
    private Optional<User> getUsers(@PathVariable Long id){
        return  repo.findById(id);
    }

    @PostMapping("/users")
    private User addUser(@RequestBody User newUser){
        return repo.save(newUser);
    }

    @DeleteMapping("/users/{id}")
    private void removeUser(@PathVariable Long id){
        repo.deleteById(id);
    }

    @PutMapping("/users/{id}")
    private User updateUser(@RequestBody User newUser, @PathVariable Long id){
        return repo.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setRole(newUser.getRole());
                    return repo.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUser_id(Math.toIntExact(id));

                    return repo.save(newUser);
                });
    }
}
