package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Lesson;
import com.example.demo.entity.Course;
import com.example.demo.repository.LessonRepository;
import com.example.demo.repository.CourseRepository;

import java.util.List;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/{courseId}")
    public Lesson addLesson(@PathVariable Long courseId, @RequestBody Lesson lesson) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }

    @GetMapping("/{courseId}")
    public List<Lesson> getLessons(@PathVariable Long courseId) {
        return lessonRepository.findByCourseIdOrderByOrderNumber(courseId);
    }
}