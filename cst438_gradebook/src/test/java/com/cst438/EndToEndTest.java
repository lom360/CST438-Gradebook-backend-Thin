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
 
public class EndToEndTest {
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
 
			// get the 2 multiply factors
//			String factora = driver.findElement(By.id("factora")).getText();
//			String factorb = driver.findElement(By.id("factorb")).getText();

 
			we = driver.findElement(By.id("addForm"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			// enter the answer.  
			// find the input tag with name="attempt"
//			String attempt = Integer.toString(Integer.parseInt(factora) * Integer.parseInt(factorb));
//			we = driver.findElement(By.name("attempt"));
//			we.sendKeys(attempt);
			we = driver.findElement(By.name("assignmentName"));
			we.sendKeys(assignment);
			
			
			// enter an alias name
			we = driver.findElement(By.name("dueDate"));
			we.sendKeys(date);
			
			// enter an alias name
			we = driver.findElement(By.name("courseId"));
			we.sendKeys(course);
			
			// find and click the submit button
			we = driver.findElement(By.id("submit"));
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			// verify the correct message
//			we = driver.findElement(By.id("message"));
//			String message = we.getText();
//			assertEquals("Correct", message);
			
			// switch to the history page
			// click on the <a> link for history
			
//			we = driver.findElement(By.xpath("//a[@href='/history']"));
//			we = driver.findElement(By.xpath("//input[@type='radio']"));
//			we = driver.findElements(By.cssSelector("input[type='radio']:last-of-type"));
			List<WebElement> we_list = driver.findElements(By.cssSelector("input[type='radio']:last-of-type"));
			we = we_list.get(we_list.size()-1);
			we.click();
			Thread.sleep(SLEEP_DURATION);
			
			// find all table cell entries
			// DataGrid uses a <div> with 
                      // class='MuiDataGrid-cellContent' to display cell data
			
//			List<WebElement> we_list = driver.findElements(
//                           By.ByClassName.className("MuiDataGrid-cellContent"));
			
			// check that first row of table contains
			//    the two factors and shows a text value of correct
//			assertEquals(factora, we_list.get(0).getText());
//			assertEquals(factorb, we_list.get(1).getText());
//			assertEquals("true", we_list.get(4).getText());
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
		
		@Test
		public void addAssignmentFail() throws Exception {
 
			System.setProperty("webdriver.edge.driver",
							EDGE_DRIVER_FILE_LOCATION);
			WebDriver driver = new EdgeDriver();
			
			try {
				WebElement we;
				
				driver.get(URL);
				// must have a short wait to allow time
                             //  for the page to download 
				Thread.sleep(SLEEP_DURATION);
 
				// get the 2 multiply factors
				String factora = driver.findElement(
                                    By.id("factora")).getText();
				String factorb = driver.findElement(
                                     By.id("factorb")).getText();
				
				// enter an incorrect answer.  
				// find the input tag with name="attempt"
				String attempt = Integer.toString(
                               Integer.parseInt(factora) 
                               + Integer.parseInt(factorb) );
				we = driver.findElement(By.name("attempt"));
				we.sendKeys(attempt);
				
				// enter an alias name
				we = driver.findElement(By.name("alias"));
				we.sendKeys(ALIAS_NAME);
				
				// find and click the submit button
				we = driver.findElement(By.id("submit"));
				we.click();
				Thread.sleep(SLEEP_DURATION);
				
				// verify the correct message
				we = driver.findElement(By.id("message"));
				String message = we.getText();
				assertEquals("Not correct. Try again", message);
				
				// switch to the history page
				// click on the <a> link for history
				
				we = driver
                               .findElement(By.xpath("//a[@href='/history']"));
				we.click();
				Thread.sleep(SLEEP_DURATION);
				
				// find all table cell entries
				// DataGrid uses a <div> with 
                             // class='MuiDataGrid-cellContent' to display cell data
				
//				List<WebElement> we_list = 
//                                  driver.findElements( By.ByClassName
//                                       .className("MuiDataGrid-cellContent"));
				
				// check that first row of table contains
				//    the two factors and shows a text value of correct
//				assertEquals(factora, we_list.get(0).getText());
//				assertEquals(factorb, we_list.get(1).getText());
//				assertEquals("false", we_list.get(4).getText());
				
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
				
			} finally {
				driver.quit();
			}
 
 
	}
}
