package com.jeanfraga.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class CourseDTO extends RepresentationModel<CourseDTO> implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long key;
	private String name;
	private String session;
	
	private SubjectDTO subject;
	private TeacherDTO teacher;
	
	private List<StudentDTO> students = new ArrayList<>();
	
	public CourseDTO() {}
	public CourseDTO(Long key, String name, String session) {
		this.key = key;
		this.name = name;
		this.session = session;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
	}

	public List<StudentDTO> getStudents() {
		return students;
	}
	
	public void enrollStudent(StudentDTO studentDTO) {
		this.students.add(studentDTO);
	}
	public void enrollListStudents(List<StudentDTO> studentsDTO) {
		this.students.addAll(studentsDTO);
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, name, session, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseDTO other = (CourseDTO) obj;
		return Objects.equals(key, other.key) && Objects.equals(name, other.name)
				&& Objects.equals(session, other.session) && Objects.equals(subject, other.subject);
	}
}
