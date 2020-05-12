package com.empdataapi.testcases;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.empdataapi.base.EmpBaseTest;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;

//NOTE: Change to work with reqres.in, problems with API Methods on previous website (http://dummy.restapiexample.com/api/v1)

public class TC1_Get_All_Employee_Data extends EmpBaseTest {

	@BeforeClass
	@Parameters({"baseurl","all_emp_data"})
	void getAllEmployees(String baseurl, String all_emp_data) throws InterruptedException {		
		logger.info("*********STARTED TC1_Get_All_Employee_Data*********");
		RestAssured.baseURI = baseurl;
		httpRequest = RestAssured.given();
		response = httpRequest.request(Method.GET, all_emp_data);
		Thread.sleep(5000);
	}
	
	@Test(priority=1)
	void checkResponseBody() throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE RESPONSE BODY ********");
		logger.info("Response Body==>"+response.getBody().asString());
		if (response.getBody().asString() != null) {
			logger.info("******** EMPLOYEE DATA RETRIEVAL SUCCESSFUL ********");
		} else if (response.getBody().asString() == null) {
			logger.error("******** EMPLOYEE DATA RETRIEVAL FAILED ********");
		}
		sa.assertEquals(response.getBody().asString() != null, true);
		sa.assertAll();
		Thread.sleep(5000);
	}
	
	@Test(priority=2)
	@Parameters("statuscode")
	void checkStatusCode(int statuscode) throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE STATUS CODE ********");
		logger.info("Status code==>"+response.getStatusCode());
		if (statuscode == response.getStatusCode()) {
			logger.info("******** STATUS CODE VALIDATION SUCCESSFUL ********");
		} else if (statuscode != response.getStatusCode()) {
			logger.error("******** STATUS CODE VALIDATION FAILED ********");
		}
		sa.assertEquals(statuscode == response.getStatusCode(), true);
		sa.assertAll();
		Thread.sleep(5000);
	}
	
	@Test(priority=3)
	@Parameters("statusline")
	void checkStatusLine(String statusline) throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE STATUS LINE ********");
		logger.info("Status line==>"+response.getStatusLine());
		if (statusline.equals(response.getStatusLine())) {
			logger.info("******** STATUS LINE VALIDATION SUCCESSFUL ********");
		} else if (!statusline.equals(response.getStatusLine()))	{
			logger.error("******** STATUS LINE VALIDATION FAILED ********");
		}
		sa.assertEquals(statusline.equals(response.getStatusLine()), true);
		sa.assertAll();
		Thread.sleep(5000);
	}
	
	@Test(priority=4)
	@Parameters("contenttype")
	void checkContentType(String contenttype) throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE CONTENT TYPE ********");
		logger.info("Content Type==>"+response.getContentType());
		if (contenttype.equals(response.getContentType())) {
			logger.info("******** CONTENT TYPE VALIDATION SUCCESSFUL ********");
		} else if (!contenttype.equals(response.getContentType()))	{
			logger.error("******** CONTENT TYPE VALIDATION FAILED ********");
		}
		sa.assertEquals(contenttype.equals(response.getContentType()), true);
		sa.assertAll();
		Thread.sleep(5000);
	}	
	
	@Test(priority=5)
	@Parameters("servertype")
	void checkServerType(String servertype) throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE SERVER TYPE ********");
		logger.info("Server Type==>"+response.header("Server"));
		if (servertype.equals(response.header("Server"))) {
			logger.info("******** SERVER TYPE VALIDATION SUCCESSFUL ********");
		} else if (!servertype.equals(response.header("Server")))	{
			logger.error("******** SERVER TYPE VALIDATION FAILED ********");
		}
		sa.assertEquals(servertype.equals(response.header("Server")), true);
		sa.assertAll();
		Thread.sleep(5000);
	}
	
	@Test(priority=6)
	@Parameters("contentencoding")
	void checkContentEncoding(String contentencoding) throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE CONTENT ENCODING ********");
		logger.info("Content Encoding==>"+response.header("Content-Encoding"));
		if (contentencoding.equals(response.header("Content-Encoding"))) {
			logger.info("******** CONTENT ENCODING VALIDATION SUCCESSFUL ********");
		} else if (!contentencoding.equals(response.header("Content-Encoding")))	{
			logger.error("******** CONTENT ENCODING VALIDATION FAILED ********");
		}
		sa.assertEquals(contentencoding.equals(response.header("Content-Encoding")), true);
		sa.assertAll();
		Thread.sleep(5000);
	}
	
	@Test(priority=7)
	@Parameters("contentlength")
	void checkContentLength(int contentlength) throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		logger.info("******** CHECKING THE CONTENT LENGTH ********");
		logger.info("Content Length==>"+response.header("Content-Length"));
		if (contentlength > Integer.parseInt(response.header("Content-Length"))) {
			logger.info("******** CONTENT LENGTH VALIDATION SUCCESSFUL ********");
		} else if (contentlength < Integer.parseInt(response.header("Content-Length")))	{
			logger.warn("******** CONTENT LENGTH ==> ACTUAL VALUE > EXPECTED VALUE ********");
		}
		sa.assertEquals(contentlength > Integer.parseInt(response.header("Content-Length")), true);
		sa.assertAll();
		Thread.sleep(5000);
	}
	
	@Test(priority=8)
	void checkAllEmpData() throws InterruptedException {		 
		JsonPath jsonpath =response.jsonPath();
		JSONParser parser = new JSONParser();
		String path=System.getProperty("user.dir")+"\\empdata.json";
	    try {
			Object obj = parser.parse(new FileReader(path));
			JSONObject json = (JSONObject) obj;
			JSONArray values = (JSONArray) json.get("data");
			for(int i = 0; jsonpath.get("data["+i+"].id") != null; i++) {
				SoftAssert sa = new SoftAssert();
				JSONObject employee = (JSONObject) values.get(i);
				logger.info("Now validating employee " + (i+1) + ": " + jsonpath.get("data["+i+"].employee_name"));
				Thread.sleep(2000);
				
				//VALIDATE ID
				logger.info("VALIDATING ID...");
				if(employee.get("id").equals(jsonpath.get("data["+i+"].id"))) {
					logger.info("ID CONFIRMED");
				} else {
					logger.error("ERROR VALIDATING ID");
				}
				sa.assertEquals(employee.get("id").equals(jsonpath.get("data["+i+"].id")), true);
				Thread.sleep(2000);
				
				//VALIDATE NAME
				logger.info("VALIDATING NAME...");
				if(employee.get("employee_name").equals(jsonpath.get("data["+i+"].employee_name"))) {
					logger.info("NAME CONFIRMED");
				} else {
					logger.error("ERROR VALIDATING NAME");
				}
				sa.assertEquals(employee.get("employee_name").equals(jsonpath.get("data["+i+"].employee_name")),true);
				Thread.sleep(2000);
				
				//VALIDATE AGE
				logger.info("VALIDATING AGE...");
				if(employee.get("employee_age").equals(jsonpath.get("data["+i+"].employee_age"))) {
					logger.info("AGE CONFIRMED");
				} else {
					logger.error("ERROR VALIDATING AGE");
				}
				sa.assertEquals(employee.get("employee_age").equals(jsonpath.get("data["+i+"].employee_age")),true);
				Thread.sleep(2000);
				
				//VALIDATE SALARY
				logger.info("VALIDATING SALARY...");
				if(employee.get("employee_salary").equals(jsonpath.get("data["+i+"].employee_salary"))) {
					logger.info("SALARY CONFIRMED");
				} else {
					logger.error("ERROR VALIDATING SALARY");
				}
				sa.assertEquals(employee.get("employee_salary").equals(jsonpath.get("data["+i+"].employee_salary")),true);
				sa.assertAll();
				Thread.sleep(2000);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		Thread.sleep(1000);
	}
	
	@AfterClass
	void tearDown() {
		logger.info("********FINISHED EXECUTION, TEST CASE: TC1_Get_All_Employee_Data********");
	}
}