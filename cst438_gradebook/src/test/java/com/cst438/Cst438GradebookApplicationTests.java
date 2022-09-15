package com.cst438;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Assignment;
import com.cst438.domain.AssignmentRepository;

@SpringBootTest
class Cst438GradebookApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	@Autowired
	AssignmentRepository assignmentRepository;
	
	@Test
	public void testCreate() throws ParseException {
		String sDate1="08/05/2022";  
	    Date date1=new SimpleDateFormat("yyyy/MM/dd").parse(sDate1); 
		Assignment a = new Assignment();
		a.setId(50);
		a.setName("This is a Test");
		a.setDueDate((java.sql.Date) date1);
		a.setNeedsGrading(1);
		a.getCourse().setCourse_id(123456);
		assignmentRepository.save(a);
	}
}
