package com.example.demo.controller;

import com.example.demo.dao.StudentRepo;
import com.example.demo.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepo repo;

    @GetMapping("/students")
    private List<Student> getAllStudents(){
        return (List<Student>) repo.findAll();
    }

    @GetMapping("/student/{id}")
    private Optional<Student> getStudents(@PathVariable Long id){
        return  repo.findById(id);
    }

    @PostMapping("/students")
    private Student addStudent(@RequestBody Student newStudent){
        return repo.save(newStudent);
    }

    @DeleteMapping("/students/{id}")
    private void removeStudent(@PathVariable Long id){
        repo.deleteById(id);
    }

    @PutMapping("/students/{id}")
    private Student updateStudent(@RequestBody Student newStudent, @PathVariable Long id){
        return repo.findById(id)
                .map(student -> {
                    student.setName(newStudent.getName());
                    student.setGrade(newStudent.getGrade());
                    return repo.save(student);
                })
                .orElseGet(() -> {
                    newStudent.setStudent_id(Math.toIntExact(id));
                    return repo.save(newStudent);
                });
    }
}
