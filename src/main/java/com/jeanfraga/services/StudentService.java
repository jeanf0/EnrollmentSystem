package com.jeanfraga.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jeanfraga.controllers.StudentController;
import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.data.dto.StudentDTO;
import com.jeanfraga.exceptions.ResourceNotFoundException;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Student;
import com.jeanfraga.repositories.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	
	@Autowired
	private PagedResourcesAssembler<StudentDTO> assembler;
	
	
	public PagedModel<EntityModel<StudentDTO>> findAll(Pageable pageable) {
		var studentsPage = studentRepository.findAll(pageable);
		var studentsDTOsPage = studentsPage.map(s -> Mapper.parseObject(s, StudentDTO.class));
		studentsDTOsPage.map(s -> s.add(linkTo(methodOn(StudentController.class).findById(s.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(StudentController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(studentsDTOsPage, link);
	}
	
	public PagedModel<EntityModel<StudentDTO>> findAllStudentByName(String firstName, Pageable pageable) {
		var studentsPage = studentRepository.findByFirstNameContaining(firstName, pageable);
		var studentsDTOsPage = studentsPage.map(s -> Mapper.parseObject(s, StudentDTO.class));
		studentsDTOsPage.map(s -> s.add(linkTo(methodOn(StudentController.class).findById(s.getKey())).withSelfRel()));
		
		
		Link link = linkTo(
				methodOn(StudentController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(studentsDTOsPage, link);
	}
	
	public StudentDTO findById(Long id) {
		var entity = studentRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		
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
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		
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
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		studentRepository.delete(entity);
	}
	
	public List<CourseDTO> listCoursesFromStudent(Long id) {
		var student = studentRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		
		var coursesVo = Mapper.parseListObjects(student.getCourses(), CourseDTO.class);
		coursesVo.stream().forEach(c -> c.add(linkTo(methodOn(StudentController.class).listCoursesFromStudent(id)).withSelfRel()));
		
		return coursesVo;
	}
	
	
}
