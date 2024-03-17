package com.jeanfraga.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanfraga.controllers.StudentController;
import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.data.dto.StudentDTO;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Student;
import com.jeanfraga.repositories.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	public List<StudentDTO> findAll() {
		var entities = studentRepository.findAll();
		var students = Mapper.parseListObjects(entities, StudentDTO.class);
		students.stream().forEach(s -> s.add(linkTo(methodOn(StudentController.class).findById(s.getKey())).withSelfRel()));
		
		return students;
	}
	
	public StudentDTO findById(Long id) {
		var entity = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		
		var vo = Mapper.parseObject(entity,StudentDTO.class);
		vo.add(linkTo(methodOn(StudentController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public StudentDTO create(StudentDTO studentDTO) {
		var entity = Mapper.parseObject(studentDTO, Student.class);
		
		var vo = Mapper.parseObject(studentRepository.save(entity), StudentDTO.class);
		vo.add(linkTo(methodOn(StudentController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public StudentDTO update(StudentDTO studentDTO) {
		var student = studentRepository.findById(studentDTO.getKey())
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		
		student.setFirstName(studentDTO.getFirstName());
		student.setLastName(studentDTO.getLastName());
		student.setBirthday(studentDTO.getBirthday());
		
		var entity = studentRepository.save(student);
		
		var vo = Mapper.parseObject(entity ,StudentDTO.class);
		vo.add(linkTo(methodOn(StudentController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete(Long id) {
		var entity = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		studentRepository.delete(entity);
	}
	
	public List<CourseDTO> listCoursesFromStudent(Long id) {
		var student = studentRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Student not found!"));
		
		var coursesVo = Mapper.parseListObjects(student.getCourses(), CourseDTO.class);
		coursesVo.stream().forEach(c -> c.add(linkTo(methodOn(StudentController.class).listCoursesFromStudent(id)).withSelfRel()));
		
		return coursesVo;
	}
	
	
}
