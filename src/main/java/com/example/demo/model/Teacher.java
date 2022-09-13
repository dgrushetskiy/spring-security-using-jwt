package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teacher_id;
    private String name;
    private String subject;

    public Teacher(){

    }
    public Teacher(String name, String grade) {
        this.name = name;
        this.subject = grade;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int student_id) {
        this.teacher_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return subject;
    }

    public void setGrade(String grade) {
        this.subject = grade;
    }
}
