package com.jeanfraga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.services.CourseService;

@RestController
@RequestMapping("/course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping
	public ResponseEntity<List<CourseDTO>> findAll() {
		return ResponseEntity.ok(courseService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CourseDTO> findById(@PathVariable(value = "id") Long id) {
		return ResponseEntity.ok(courseService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<CourseDTO> create(@RequestBody CourseDTO courseDTO) {
		return ResponseEntity.status(201).body(courseService.create(courseDTO));
	}
	
	@PutMapping
	public ResponseEntity<CourseDTO> update(@RequestBody CourseDTO courseDTO) {
		return ResponseEntity.ok(courseService.update(courseDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		courseService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{courseId}/subject/{subjectId}")
	public ResponseEntity<CourseDTO> setSubject(@PathVariable Long courseId, @PathVariable Long subjectId) {
		return ResponseEntity.ok(courseService.setSubject(courseId, subjectId));
	}
	
	@PutMapping("/{courseId}/teacher/{teacherId}")
	public ResponseEntity<CourseDTO> assignTeacher(@PathVariable Long courseId, @PathVariable Long teacherId) {
		return ResponseEntity.ok(courseService.assignTeacher(courseId, teacherId));
	}
	
	@PutMapping("/{courseId}/student/{studentId}")
	public ResponseEntity<CourseDTO> enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
		return ResponseEntity.ok(courseService.enrollStudent(courseId, studentId));
	}

}
