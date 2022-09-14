package com.example.demo.controller;

import com.example.demo.dao.UserRepo;
import com.example.demo.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepo repo;

    @GetMapping("/users")
    private List<AppUser> getAllUsers(){
        return (List<AppUser>) repo.findAll();
    }

    @GetMapping("/user/{id}")
    private Optional<AppUser> getUsers(@PathVariable Long id){
        return  repo.findById(id);
    }

    @PostMapping("/users")
    private AppUser addUser(@RequestBody AppUser newAppUser){
        return repo.save(newAppUser);
    }

    @DeleteMapping("/users/{id}")
    private void removeUser(@PathVariable Long id){
        repo.deleteById(id);
    }

    @PutMapping("/users/{id}")
    private AppUser updateUser(@RequestBody AppUser newAppUser, @PathVariable Long id){
        return repo.findById(id)
                .map(user -> {
                    user.setName(newAppUser.getName());
                    user.setRole(newAppUser.getRole());
                    return repo.save(user);
                })
                .orElseGet(() -> {
                    newAppUser.setUser_id(Math.toIntExact(id));

                    return repo.save(newAppUser);
                });
    }
}
