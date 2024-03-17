package com.jeanfraga.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.jeanfraga.data.dto.CourseDTO;
import com.jeanfraga.data.dto.StudentDTO;
import com.jeanfraga.data.dto.SubjectDTO;
import com.jeanfraga.data.dto.TeacherDTO;
import com.jeanfraga.models.Course;
import com.jeanfraga.models.Student;
import com.jeanfraga.models.Subject;
import com.jeanfraga.models.Teacher;

public class Mapper {

	private static ModelMapper mapper = new ModelMapper();
	
	static {
		mapper.createTypeMap(Course.class, CourseDTO.class).addMappings(mapper -> {
			mapper.map(src -> src.getId(), CourseDTO::setKey);
			mapper.map(src -> src.getSubject(), CourseDTO::setSubject);
			mapper.map(src -> src.getTeacher(), CourseDTO::setTeacher);
			mapper.map(src -> src.getStudents(), CourseDTO::enrollListStudents);
		});
		
		mapper.createTypeMap(CourseDTO.class, Course.class).addMappings(mapper -> {
			mapper.map(src -> src.getKey(), Course::setId);
			mapper.map(src -> src.getSubject(), Course::setSubject);
			mapper.map(src -> src.getTeacher(), Course::setTeacher);
			mapper.map(src -> src.getStudents(), Course::enrollListStudents);
		});
		
		mapper.createTypeMap(Subject.class, SubjectDTO.class).addMapping(Subject::getId, SubjectDTO::setKey);
		mapper.createTypeMap(SubjectDTO.class, Subject.class).addMapping(SubjectDTO::getKey, Subject::setId);
		
		mapper.createTypeMap(Student.class, StudentDTO.class).addMapping(Student::getId, StudentDTO::setKey);
		mapper.createTypeMap(StudentDTO.class, Student.class).addMapping(StudentDTO::getKey, Student::setId);
		
		mapper.createTypeMap(Teacher.class, TeacherDTO.class).addMapping(Teacher::getId, TeacherDTO::setKey);
		mapper.createTypeMap(TeacherDTO.class, Teacher.class).addMapping(TeacherDTO::getKey, Teacher::setId);
		
	}
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
		return mapper.map(origin, destination);
	}
	
	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination ){
		List<D> destinationList = new ArrayList<>();
		for(O o: origin) {
			destinationList.add(mapper.map(o, destination));
		}
		return destinationList;
	}
}
