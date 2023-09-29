package com.visa.schoolservice;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public School save(@RequestBody School school) {
        return service.saveSchool(school);
    }

    @GetMapping
    public List<School> findAllSchools() {
        return service.findAllSchools();
    }

    @GetMapping("/with-students/{school-id}")
    public SchoolResponse findAllSchools(@PathVariable("school-id") Integer schoolId) {
        return service.findSchoolsWithStudents(schoolId);
    }
}