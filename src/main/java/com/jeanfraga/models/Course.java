package com.jeanfraga.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="COURSE")
public class Course implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String session;
	
	@ManyToOne
	@JoinColumn(name="subject_id", referencedColumnName = "id")
	private Subject subject;
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	@JsonIgnoreProperties("courses")
	private Teacher teacher;
	
	@ManyToMany
	@JoinTable(name="Course_Student", 
			joinColumns= {@JoinColumn(name="course_id")}, 
			inverseJoinColumns = {@JoinColumn(name="student_id")})
	private List<Student> students = new ArrayList<>();
	
	public Course() {}
	public Course(Long id, String name, String session) {
		this.id = id;
		this.name = name;
		this.session = session;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}
	
	public void enrollStudent(Student student) {
		this.students.add(student);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, session, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(session, other.session) && Objects.equals(subject, other.subject);
	}
}
