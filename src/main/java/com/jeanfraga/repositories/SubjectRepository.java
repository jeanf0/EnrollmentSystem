package com.jeanfraga.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jeanfraga.models.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long>{

	Page<Subject> findByNameContaining(String name, Pageable pageable);
}
