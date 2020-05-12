package com.empdataapi.testcases;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.empdataapi.base.EmpBaseTest;
import com.empdataapi.utilities.XLUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC3_Create_New_Employee extends EmpBaseTest {
	
	@BeforeClass
	void init() {
		logger.info("*********STARTED TC3_Create_New_Employee*********");	
	}
	
	@Test(dataProvider="empdataprovider")
	void postEmployee(String name, String salary, String age, String statuscode, String statusline, String contenttype, String servertype, String contentlength) throws InterruptedException {
		this.emp_name = name;
		this.emp_sal = salary;
		this.emp_age = age;
		this.resp_statcode = statuscode;
		this.resp_statline = statusline;
		this.resp_conttype = contenttype;
		this.resp_sertype = servertype;
		this.resp_contlen = contentlength;
		
		RestAssured.baseURI=this.url;
		httpRequest=RestAssured.given();
		
		JSONObject requestParams=new JSONObject();
		
		requestParams.put("name",name);
		requestParams.put("salary",salary);
		requestParams.put("age",age);
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		response=httpRequest.request(Method.POST,"/create");		
		jsonpath = response.jsonPath();
		Thread.sleep(5000);
		callMethods();
	}
	
	public void callMethods() throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		checkResponseBody();
		sa.assertEquals(checkEmpName(),true);
		sa.assertEquals(checkEmpAge(),true);
		sa.assertEquals(checkEmpSalary(),true);
		sa.assertEquals(checkStatusCode(),true);
		sa.assertEquals(checkStatusLine(),true);
		sa.assertEquals(checkContentType(),true);
		sa.assertEquals(checkServerType(),true);
		sa.assertEquals(checkContentLength(),true);
		sa.assertAll();
	}
	
	public void checkResponseBody() throws InterruptedException {
		logger.info("TRIED ADDING RECORD ==> "+response.getBody().asString());		
		Thread.sleep(5000);
	}
	
	public boolean checkEmpName() throws InterruptedException {
		boolean name_test;
		logger.info("******** CHECKING NAME ********");
		logger.info("NAME ==> "+jsonpath.get("data.name"));
		name_test = emp_name.equals(jsonpath.get("data.name")); 
		if(name_test) {
			logger.info("********NAME ADDED SUCCESSFULLY********");
		} else if (!name_test) {
			logger.error("********FAILED TO ADD NAME ********");
		}
		Thread.sleep(5000);
		return name_test;
	}
	
	public boolean checkEmpAge() throws InterruptedException {
		boolean age_test;
		logger.info("******** CHECKING AGE ********");
		logger.info("AGE ==> "+jsonpath.get("data.age"));
		age_test = emp_age.equals(jsonpath.get("data.age")); 
		if(age_test) {
			logger.info("********AGE ADDED SUCCESSFULLY********");
		} else if (!age_test) {
			logger.error("********FAILED TO ADD AGE********");
		}
		Thread.sleep(5000);
		return age_test;
	}
	
	public boolean checkEmpSalary() throws InterruptedException {
		boolean sal_test;
		logger.info("******** CHECKING SALARY ********");
		logger.info("SALARY ==> "+jsonpath.get("data.salary"));
		sal_test = emp_sal.equals(jsonpath.get("data.salary")); 
		if(sal_test) {
			logger.info("********SALARY ADDED SUCCESSFULLY********");
		} else if (!sal_test) {
			logger.error("********FAILED TO ADD SALARY********");
		}
		Thread.sleep(5000);
		return sal_test;
	}
	
	public boolean checkStatusCode() throws InterruptedException {
		boolean statcode_test;
		logger.info("******** CHECKING THE STATUS CODE ********");
		logger.info("STATUS CODE ==> "+response.getStatusCode());
		statcode_test = Integer.parseInt(this.resp_statcode) == response.getStatusCode();
		if (statcode_test) {
			logger.info("******** STATUS CODE VALIDATION SUCCESSFUL ********");
		} else if (!statcode_test) {
			logger.error("******** STATUS CODE VALIDATION FAILED ********");
		}		
		Thread.sleep(5000);
		return statcode_test;
	}
	
	public boolean checkStatusLine() throws InterruptedException {
		boolean statline_test;
		logger.info("******** CHECKING THE STATUS LINE ********");
		logger.info("STATUS LINE ==> "+response.getStatusLine());
		statline_test = this.resp_statline.equals(response.getStatusLine());
		if (statline_test) {
			logger.info("******** STATUS LINE VALIDATION SUCCESSFUL ********");
		} else if (!statline_test)	{
			logger.error("******** STATUS LINE VALIDATION FAILED ********");
		}
		Thread.sleep(5000);
		return statline_test;
	}

	public boolean checkContentType() throws InterruptedException {
		boolean contenttype_test;
		logger.info("******** CHECKING THE CONTENT TYPE ********");
		logger.info("CONTENT TYPE ==> "+response.getContentType());
		contenttype_test = this.resp_conttype.equals(response.getContentType()); 
		if (contenttype_test) {
			logger.info("******** CONTENT TYPE VALIDATION SUCCESSFUL ********");
		} else if (!contenttype_test)	{
			logger.error("******** CONTENT TYPE VALIDATION FAILED ********");
		}
		Thread.sleep(5000);
		return contenttype_test;
	}	
	
	public boolean checkServerType() throws InterruptedException {
		boolean servertype_test;
		logger.info("******** CHECKING THE SERVER TYPE ********");
		logger.info("SERVER TYPE ==> "+response.header("Server"));
		servertype_test = this.resp_sertype.equals(response.header("Server"));
		if (servertype_test) {
			logger.info("******** SERVER TYPE VALIDATION SUCCESSFUL ********");
		} else if (!servertype_test)	{
			logger.error("******** SERVER TYPE VALIDATION FAILED ********");
		}
		Thread.sleep(5000);
		return servertype_test;
	}
	
	public boolean checkContentLength() throws InterruptedException {
		boolean contentlength_test;
		logger.info("******** CHECKING THE CONTENT LENGTH ********");
		logger.info("CONTENT LENGTH ==> "+response.header("Content-Length"));
		contentlength_test = Integer.parseInt(this.resp_contlen) > Integer.parseInt(response.header("Content-Length"));
		if (contentlength_test) {
			logger.info("******** CONTENT LENGTH VALIDATION SUCCESSFUL ********");
		} else if (!contentlength_test)	{
			logger.warn("******** CONTENT LENGTH ==> ACTUAL VALUE > EXPECTED VALUE ********");
		}
		Thread.sleep(5000);
		return contentlength_test;
	}	
	
	@DataProvider(name="empdataprovider")
	String [][] getEmpData() throws IOException {		
		//Read data from excel		
		String path=System.getProperty("user.dir")+"\\empdata.xlsx";		
		int rownum=XLUtils.getRowCount(path,"POST");
		int colcount=XLUtils.getCellCount(path,"POST",1);		
		String empdata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++) {
			for(int j=0;j<colcount;j++) {
				empdata[i-1][j]=XLUtils.getCellData(path, "POST", i, j);
			}
		}
		return empdata;
	}
	
	@AfterClass
	void tearDown() {
		logger.info("********FINISHED EXECUTION, TEST CASE: TC3_Create_New_Employee********");
	}
}