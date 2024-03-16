package com.jeanfraga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.data.dto.StudentDTO;
import com.jeanfraga.data.dto.SubjectDTO;
import com.jeanfraga.data.dto.TeacherDTO;
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

		return Mapper.parseListObjects(entities, CourseDTO.class);
	}

	public CourseDTO findById(Long id) {
		var entity = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found!"));

		var vo = Mapper.parseObject(entity, CourseDTO.class);

		if (entity.getSubject() != null) {
			var subjectVO = Mapper.parseObject(entity.getSubject(), SubjectDTO.class);
			vo.setSubject(subjectVO);
		}
		
		if (entity.getTeacher() != null) {
			var teacherVO = Mapper.parseObject(entity.getTeacher(), TeacherDTO.class);
			vo.setTeacher(teacherVO);
		}
		if (!entity.getStudents().isEmpty()) {
			var studentsVO = Mapper.parseListObjects(entity.getStudents(), StudentDTO.class);
			vo.getStudents().addAll(studentsVO);
		}
		return vo;
	}

	public CourseDTO create(CourseDTO courseDTO) {
		var entity = Mapper.parseObject(courseDTO, Course.class);
		entity = courseRepository.save(entity);
		var vo = Mapper.parseObject(entity, CourseDTO.class);

		return vo;
	}

	public CourseDTO update(CourseDTO courseDTO) {
		var course = courseRepository.findById(courseDTO.getId())
				.orElseThrow(() -> new RuntimeException("Course not found!"));

		course.setName(courseDTO.getName());
		course.setSession(courseDTO.getSession());

		var entity = courseRepository.save(course);

		return Mapper.parseObject(entity, CourseDTO.class);
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
		var subjectVO = Mapper.parseObject(entity.getSubject(), SubjectDTO.class);

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.setSubject(subjectVO);
		return vo;

	}

	public CourseDTO assignTeacher(Long courseId, Long teacherId) {

		Course course = courseRepository.findById(courseId).get();
		Teacher teacher = teacherRepository.findById(teacherId).get();

		course.setTeacher(teacher);
		var entity = courseRepository.save(course);
		var teacherVO = Mapper.parseObject(entity.getTeacher(), TeacherDTO.class);

		var vo = Mapper.parseObject(entity, CourseDTO.class);
		vo.setTeacher(teacherVO);
		return vo;

	}

	public CourseDTO enrollStudent(Long courseId, Long studentId) {

		Course course = courseRepository.findById(courseId).get();
		Student student = studentRepository.findById(studentId).get();

		course.enrollStudent(student);
		var entity = courseRepository.save(course);

		var vo = Mapper.parseObject(entity, CourseDTO.class);

		var studentsVO = Mapper.parseListObjects(entity.getStudents(), StudentDTO.class);
		vo.getStudents().addAll(studentsVO);

		return vo;

	}

}
