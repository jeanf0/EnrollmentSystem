package com.jeanfraga.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jeanfraga.models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long>{

}
