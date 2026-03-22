package com.example.demo.dto;

import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<CourseDTO> courses;

    public UserResponseDTO(Long id, String name, String email, List<CourseDTO> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }
}