package com.example.demo.controller;

import com.example.demo.dao.StudentRepo;
import com.example.demo.model.AuthenticationRequest;
import com.example.demo.model.AuthenticationResponse;
import com.example.demo.model.Student;
import com.example.demo.service.AppUserDetailsService;
import com.example.demo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {

    @Autowired
    private StudentRepo repo;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        System.out.println("createAuthenticationToken called with " + authenticationRequest.getUsername()
         + " " + authenticationRequest.getPassword());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
            System.out.println("Authenticated successfully");
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        //final String jwt = "1234";
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
