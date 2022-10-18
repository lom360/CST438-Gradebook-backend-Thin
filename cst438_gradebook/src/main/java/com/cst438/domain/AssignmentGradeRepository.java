package com.cst438.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AssignmentGradeRepository extends CrudRepository <AssignmentGrade, Integer> {
	
	@Query("select a from AssignmentGrade a where a.assignment.id=:assignmentId and a.studentEnrollment.studentEmail=:email")
	AssignmentGrade findByAssignmentIdAndStudentEmail(
			@Param("assignmentId") int assignmentId, 
			@Param("email") String email );
	
	@Query(value="select score from Assignment_Grade where assignment_id= ? and enrollment_id= ?", nativeQuery=true)
	String findScoreByAssignmentIdAndEnrollmentId(int assignmentId, int enrollmentId);
}
