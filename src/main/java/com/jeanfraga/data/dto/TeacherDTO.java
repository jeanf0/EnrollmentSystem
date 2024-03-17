package com.jeanfraga.data.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class TeacherDTO extends RepresentationModel<TeacherDTO> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long key;
	private String firstName;
	private String lastName;
	private LocalDate birthday;
	
	public TeacherDTO() {}

	public TeacherDTO(Long key, String firstName, String lastName, LocalDate birthday) {
		this.key = key;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	@Override
	public int hashCode() {
		return Objects.hash(birthday, firstName, key, lastName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TeacherDTO other = (TeacherDTO) obj;
		return Objects.equals(birthday, other.birthday) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(key, other.key) && Objects.equals(lastName, other.lastName);
	}
}
