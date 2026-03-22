package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.Course;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.dto.CourseDTO;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.demo.security.JwtUtil;
import com.example.demo.dto.LoginResponseDTO;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Handle role properly
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("ROLE_STUDENT");
        } 
        else if (user.getRole().equalsIgnoreCase("ADMIN")) {
            user.setRole("ROLE_ADMIN");
        } 
        else if (user.getRole().equalsIgnoreCase("STUDENT")) {
            user.setRole("ROLE_STUDENT");
        }

        return userRepository.save(user);
    }

    public List<UserResponseDTO> getAllUsersDTO() {

        return userRepository.findAll().stream().map(user -> {

            List<CourseDTO> courseDTOs = user.getCourses().stream()
                    .map(course -> new CourseDTO(course.getId(), course.getTitle()))
                    .collect(Collectors.toList());

            return new UserResponseDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    courseDTOs
            );

        }).collect(Collectors.toList());
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User updatedUser) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void enrollUserInCourse(Long userId, Long courseId) {
    	System.out.println("Enroll request: userId=" + userId + ", courseId=" + courseId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // prevent duplicate enrollment
        if (user.getCourses().contains(course)) {
            throw new RuntimeException("User already enrolled in this course");
        }

        user.getCourses().add(course);

        userRepository.save(user);
    }

    public LoginResponseDTO login(String username, String rawPassword) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(username, user.getRole());

        return new LoginResponseDTO(token, user.getId());
    }
}