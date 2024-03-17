package com.jeanfraga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.jeanfraga.controllers.CourseController;
import com.jeanfraga.data.dto.CourseDTO;
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

	public List<CourseDTO> findAll() {
		var entities = courseRepository.findAll();

		var courses =  Mapper.parseListObjects(entities, CourseDTO.class);
		courses.stream().forEach(c -> c.add(linkTo(methodOn(CourseController.class).findById(c.getKey())).withSelfRel()));
		return courses;
	}

	public CourseDTO findById(Long id) {
		var entity = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found!"));

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
				.orElseThrow(() -> new RuntimeException("Course not found!"));

		course.setName(courseDTO.getName());
		course.setSession(courseDTO.getSession());

		var entity = courseRepository.save(course);

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	public void delete(Long id) {
		var entity = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found!"));
		courseRepository.delete(entity);
	}

	public CourseDTO setSubject(Long courseId, Long subjectId) {

		Course course = courseRepository.findById(courseId).get();
		Subject subject = subjectRepository.findById(subjectId).get();

		course.setSubject(subject);
		var entity = courseRepository.save(course);
		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}

	public CourseDTO assignTeacher(Long courseId, Long teacherId) {

		Course course = courseRepository.findById(courseId).get();
		Teacher teacher = teacherRepository.findById(teacherId).get();
		
		course.setTeacher(teacher);
		var entity = courseRepository.save(course);
		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;
		
	}

	public CourseDTO enrollStudent(Long courseId, Long studentId) {

		Course course = courseRepository.findById(courseId).get();
		Student student = studentRepository.findById(studentId).get();

		course.enrollStudent(student);
		var entity = courseRepository.save(course);

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.add(linkTo(methodOn(CourseController.class).findById(vo.getKey())).withSelfRel());
		return vo;

	}

}
