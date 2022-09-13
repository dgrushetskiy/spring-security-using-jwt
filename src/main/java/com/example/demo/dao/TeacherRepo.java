package com.example.demo.dao;

import com.example.demo.model.Student;
import com.example.demo.model.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepo extends CrudRepository<Teacher, Long> {}
