package com.jeanfraga.data.dto;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class SubjectDTO extends RepresentationModel<SubjectDTO> implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long key;
	private String name;
	private String description;
	private Integer workload;
	

	public SubjectDTO() {
	}

	public SubjectDTO(Long key, String name, String description, Integer workload) {
		this.key = key;
		this.name = name;
		this.description = description;
		this.workload = workload;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getWorkload() {
		return workload;
	}

	public void setWorkload(Integer workload) {
		this.workload = workload;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, key, name, workload);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubjectDTO other = (SubjectDTO) obj;
		return Objects.equals(description, other.description) && Objects.equals(key, other.key)
				&& Objects.equals(name, other.name) && Objects.equals(workload, other.workload);
	}
}
