package com.cst438.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentRepository extends CrudRepository <Assignment, Integer> {

	@Query("select a from Assignment a where a.needsGrading=1 and a.dueDate < current_date and a.course.instructor= :email order by a.id")
	List<Assignment> findNeedGradingByEmail(@Param("email") String email);
	
//	@Query("select a from Assignment a where a.enrollment.student_email= :email order by a.id")
//	List<Assignment> findAssignmentByEmail(String email); // I ADDED THIS CODE HERE
	
	@Query(value="SELECT * FROM Assignment course_id=999001|123456",nativeQuery=true)
	List<Assignment> findAssignmentByCourse(); // I ADDED THIS CODE HERE
	
	@Query(value="select * from Assignment where course_id= ?", nativeQuery=true)
	List<Assignment> findAssignmentByCourseId(int id); // I ADDED THIS CODE HERE
}
