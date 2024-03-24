package com.jeanfraga.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import com.jeanfraga.controllers.CourseController;
import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.exceptions.ResourceNotFoundException;
import com.jeanfraga.mapper.Mapper;
import com.jeanfraga.models.Course;
import com.jeanfraga.models.Student;
import com.jeanfraga.models.Subject;
import com.jeanfraga.models.Teacher;
import com.jeanfraga.repositories.CourseRepository;
import com.jeanfraga.repositories.StudentRepository;
import com.jeanfraga.repositories.SubjectRepository;
import com.jeanfraga.repositories.TeacherRepository;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private PagedResourcesAssembler<CourseDTO> assembler;
	
	
	public PagedModel<EntityModel<CourseDTO>> findAll(Pageable pageable) {
		var coursesPage = courseRepository.findAll(pageable);
		var coursesDTOsPage = coursesPage.map(c -> Mapper.parseObject(c, CourseDTO.class));
		coursesDTOsPage.map(c -> c.add(linkTo(methodOn(CourseController.class).findById(c.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(CourseController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(coursesDTOsPage, link);
	}
	
	public PagedModel<EntityModel<CourseDTO>> findAllCourseByName(String name, Pageable pageable) {
		var coursesPage = courseRepository.findByNameContaining(name, pageable);
		var coursesDTOsPage = coursesPage.map(c -> Mapper.parseObject(c, CourseDTO.class));
		coursesDTOsPage.map(c -> c.add(linkTo(methodOn(CourseController.class).findById(c.getKey())).withSelfRel()));
		
		
		
		Link link = linkTo(
				methodOn(CourseController.class)
				.findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();
		return assembler.toModel(coursesDTOsPage, link);
	}

	public CourseDTO findById(Long id) {
		var entity = courseRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(id)).withSelfRel());
		return vo;
	}

	public CourseDTO create(CourseDTO courseDTO) {
		var entity = Mapper.parseObject(courseDTO, Course.class);
		entity = courseRepository.save(entity);
		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public CourseDTO update(CourseDTO courseDTO) {
		var course = courseRepository.findById(courseDTO.getKey())
				.orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));

		course.setName(courseDTO.getName());
		course.setSession(courseDTO.getSession());

		var entity = courseRepository.save(course);

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		var entity = courseRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));
		courseRepository.delete(entity);
	}

	public CourseDTO setSubject(Long courseId, Long subjectId) {

		Course course = courseRepository.findById(courseId).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));;
		Subject subject = subjectRepository.findById(subjectId).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));;

		course.setSubject(subject);
		var entity = courseRepository.save(course);
		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}

	public CourseDTO assignTeacher(Long courseId, Long teacherId) {

		Course course = courseRepository.findById(courseId).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));;
		Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));;
		
		course.setTeacher(teacher);
		var entity = courseRepository.save(course);
		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}

	public CourseDTO enrollStudent(Long courseId, Long studentId) {

		Course course = courseRepository.findById(courseId).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));;
		Student student = studentRepository.findById(studentId).orElseThrow(() ->  new ResourceNotFoundException("Not found resources for this ID!"));;

		course.enrollStudent(student);
		var entity = courseRepository.save(course);

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}

}
