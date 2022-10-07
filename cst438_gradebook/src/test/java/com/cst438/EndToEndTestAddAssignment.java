package com.cst438;

//package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

//
//import java.text.SimpleDateFormat;
//import java.util.List;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
 
public class EndToEndTestAddAssignment {
	public static final String EDGE_DRIVER_FILE_LOCATION 
                          = "C:/edgedriver_win64/msedgedriver.exe";
	public static final String URL = "http://localhost:3000";
	public static final String ALIAS_NAME = "test";
	public static final int SLEEP_DURATION = 1000; // 1 second.
 
	@Test
	public void addAssignmentTest() throws Exception {
 
		// set the driver location and start driver
		//@formatter:off
		//
		// browser	property name 				Java Driver Class
		// -------  ------------------------    ----------------------
		// Edge 	webdriver.edge.driver 		EdgeDriver
		// FireFox 	webdriver.firefox.driver 	FirefoxDriver
		// IE 		webdriver.ie.driver 		InternetExplorerDriver
		// Chrome   webdriver.chrome.driver     ChromeDriver
		//
		//@formatter:on
 
		//TODO update the property name for your browser 
		System.setProperty("webdriver.edge.driver",
						EDGE_DRIVER_FILE_LOCATION);
		//TODO update the class ChromeDriver()  for your browser
		WebDriver driver = new EdgeDriver();
		
		try {
			String assignment = "End To End Test";
			String date = "2012-12-23";
			String course = "123456";
			
			WebElement we;
			
			driver.get(URL);
			// must have a short wait to allow time for the page to download 
			Thread.sleep(SLEEP_DURATION);
 

			// find and click add assignment button
			we = driver.findElement(By.id("addForm"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			// enter in assignmentName field
			we = driver.findElement(By.name("assignmentName"));
			we.sendKeys(assignment);
			
			
			// enter in dueDate field
			we = driver.findElement(By.name("dueDate"));
			we.sendKeys(date);
			
			// enter in courseId field
			we = driver.findElement(By.name("courseId"));
			we.sendKeys(course);
			
			// find and click the submit button
			we = driver.findElement(By.id("submit"));
			we.click();
			Thread.sleep(SLEEP_DURATION*2);
			

			// gather a list of all radio buttons and click the last one
			List<WebElement> we_list = driver.findElements(By.cssSelector("input[type='radio']:last-of-type"));
			we = we_list.get(we_list.size()-1); // this is to click the last radio button
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			
			// click the grade button. Note the last radio button should have been clicked already
			we = driver.findElement(By.id("gradeBtn"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
			
		} finally {
			driver.quit();
		}
	}
		
	
	// This test will input a course that does not exist
	// So when the modal closes a message will appears stating assignment failed to add.
	// Afterwards will just select the first radial to go to grading page.
		@Test
		public void addAssignmentFail() throws Exception {
 
			System.setProperty("webdriver.edge.driver",
							EDGE_DRIVER_FILE_LOCATION);
			WebDriver driver = new EdgeDriver();
			
			try {
				String assignment = "End To End Test";
				String date = "2012-12-23";
				String course = "991";
				
				WebElement we;
				
				driver.get(URL);
				// must have a short wait to allow time for the page to download 
				Thread.sleep(SLEEP_DURATION);
	 

				// find and click add assignment button
				we = driver.findElement(By.id("addForm"));
				we.click();
				Thread.sleep(SLEEP_DURATION);
				
				// enter in assignmentName field
				we = driver.findElement(By.name("assignmentName"));
				we.sendKeys(assignment);
				
				
				// enter in dueDate field
				we = driver.findElement(By.name("dueDate"));
				we.sendKeys(date);
				
				// enter in courseId field
				// an incorrect courseId should be entered for this case
				we = driver.findElement(By.name("courseId"));
				we.sendKeys(course);
				
				// find and click the submit button
				we = driver.findElement(By.id("submit"));
				we.click();
				Thread.sleep(SLEEP_DURATION*2);
				
				
				// click the grade button.
				// will just select the first radial since adding assignment should have failed for this case
				we = driver.findElement(By.id("gradeBtn"));
				we.click();
				Thread.sleep(SLEEP_DURATION);
				
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
				
			} finally {
				driver.quit();
			}
 
 
	}
}
