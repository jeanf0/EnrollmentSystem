package com.jeanfraga.mapper;

import java.util.ArrayList;
import java.util.List;
import java.lang.Class;

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
		mapper.createTypeMap(Course.class, CourseDTO.class).addMapping(Course::getId, CourseDTO::setId);
		mapper.createTypeMap(CourseDTO.class, Course.class).addMapping(CourseDTO::getId, Course::setId);
		
		mapper.createTypeMap(Subject.class, SubjectDTO.class).addMapping(Subject::getId, SubjectDTO::setId);
		mapper.createTypeMap(SubjectDTO.class, Subject.class).addMapping(SubjectDTO::getId, Subject::setId);
		
		mapper.createTypeMap(Student.class, StudentDTO.class).addMapping(Student::getId, StudentDTO::setId);
		mapper.createTypeMap(StudentDTO.class, Student.class).addMapping(StudentDTO::getId, Student::setId);
		
		mapper.createTypeMap(Teacher.class, TeacherDTO.class).addMapping(Teacher::getId, TeacherDTO::setId);
		mapper.createTypeMap(TeacherDTO.class, Teacher.class).addMapping(TeacherDTO::getId, Teacher::setId);
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
