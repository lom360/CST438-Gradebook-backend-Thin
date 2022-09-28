package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EnrollmentRepository extends CrudRepository <Enrollment, Integer> {
	@Query(value="select * from Enrollment where course_id= ?", nativeQuery=true)
	List<Enrollment> findEnrollmentByCourseId(int id); // I ADDED THIS CODE HERE
}
