package com.jeanfraga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanfraga.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
