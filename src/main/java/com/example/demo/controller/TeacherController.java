package com.example.demo.controller;

import com.example.demo.dao.TeacherRepo;
import com.example.demo.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TeacherController {

    @Autowired
    private TeacherRepo repo;

    @GetMapping("/teachers")
    private List<Teacher> getAllTeachers(){
        return (List<Teacher>) repo.findAll();
    }

    @GetMapping("/teacher/{id}")
    private Optional<Teacher> getTeachers(@PathVariable Long id){
        return  repo.findById(id);
    }

    @PostMapping("/teachers")
    private Teacher addTeacher(@RequestBody Teacher newTeacher){
        return repo.save(newTeacher);
    }

    @DeleteMapping("/teachers/{id}")
    private void removeTeacher(@PathVariable Long id){
        repo.deleteById(id);
    }

    @PutMapping("/teachers/{id}")
    private Teacher updateTeacher(@RequestBody Teacher newTeacher, @PathVariable Long id){
        return repo.findById(id)
                .map(teacher -> {
                    teacher.setName(newTeacher.getName());
                    teacher.setGrade(newTeacher.getGrade());
                    return repo.save(teacher);
                })
                .orElseGet(() -> {
                    newTeacher.setTeacher_id(Math.toIntExact(id));
                    return repo.save(newTeacher);
                });
    }
}
