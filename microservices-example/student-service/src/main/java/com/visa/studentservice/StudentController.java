package com.visa.studentservice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(originPatterns = "*")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student save(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @GetMapping
    public List<Student> findAllStudents() {
        return service.findAllStudents();
    }

    @GetMapping("/school/{school-id}")
    public List<Student> findAllStudents(@PathVariable("school-id") Integer schoolId) {
        return service.findAllStudentsBySchool(schoolId);
    }
}