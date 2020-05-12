package com.empdataapi.testcases;
import java.io.IOException;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.empdataapi.base.EmpBaseTest;
import com.empdataapi.utilities.XLUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;

public class TC2_Get_Single_Employee_Data extends EmpBaseTest {
		
	@BeforeClass
	void init() {
		logger.info("*********STARTED TC2_Get_Single_Employee_Data*********");	
	}
	
	@Test(dataProvider="empdataprovider")
	void getEmployee(String id, String name, String salary, String age, String statuscode, String statusline, String contenttype, String servertype, String contentlength) throws InterruptedException {
		emp_id = id;
		emp_name = name;
		emp_sal = salary;
		emp_age = age;
		resp_statcode = statuscode;
		resp_statline = statusline;
		resp_conttype = contenttype;
		resp_sertype = servertype;
		resp_contlen = contentlength;
		
		RestAssured.baseURI = url;
		httpRequest = RestAssured.given();
		httpRequest.header("Cookie","ezCMPCCS=true; PHPSESSID=2cbfaee574a94a742c3372d974b096e2; ezoadgid_133674=-1; ezoref_133674=; ezoab_133674=mod94-c; active_template::133674=pub_site.1589103496");
		response = httpRequest.request(Method.GET, "/employee/"+id);
		jsonpath = response.jsonPath();
		Thread.sleep(5000);
		callMethods();
	}
	
	public void callMethods() throws InterruptedException {
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(checkResponseBody(),true);
		sa.assertEquals(checkEmpID(),true);
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
	
	public boolean checkResponseBody() throws InterruptedException {
		boolean rec_test;
		logger.info("******** RETRIEVING RECORD ********");
		logger.info("RECORD ==> "+response.getBody().asString());
		rec_test = response.getBody().asString() != null & !jsonpath.get("data").equals(recNotFound);
		if (rec_test) {
			logger.info("******** RECORD RETRIEVAL SUCCESSFUL ********");
		} else if (!rec_test){
			logger.error("******** PROBLEM HERE...NO RECORD FOUND!!! ********");
		}
		Thread.sleep(5000);
		return rec_test;
	}
	
	public boolean checkEmpID() throws InterruptedException {
		boolean id_test;
		logger.info("******** CHECKING EMPLOYEE ID ********");
		logger.info("ID ==> "+jsonpath.get("data.id"));
		id_test = emp_id.equals(jsonpath.get("data.id"));
		if(id_test) {
			logger.info("********ID VALIDATION SUCCESSFUL********");
		} else if (!id_test) {
			logger.error("********ID VALIDATION FAILED ********");
		}
		Thread.sleep(5000);
		return id_test;
	}
	
	public boolean checkEmpName() throws InterruptedException {
		boolean name_test;
		logger.info("******** CHECKING EMPLOYEE NAME ********");
		logger.info("NAME ==> "+jsonpath.get("data.employee_name"));
		name_test = emp_name.equals(jsonpath.get("data.employee_name")); 
		if(name_test) {
			logger.info("********NAME VALIDATION SUCCESSFUL********");
		} else if (!name_test) {
			logger.error("********NAME VALIDATION FAILED ********");
		}
		Thread.sleep(5000);
		return name_test;
	}
	
	public boolean checkEmpAge() throws InterruptedException {
		boolean age_test;
		logger.info("******** CHECKING EMPLOYEE AGE ********");
		logger.info("AGE ==> "+jsonpath.get("data.employee_age"));
		age_test = emp_age.equals(jsonpath.get("data.employee_age")); 
		if(age_test) {
			logger.info("********AGE VALIDATION SUCCESSFUL********");
		} else if (!age_test) {
			logger.error("********AGE VALIDATION FAILED ********");
		}
		Thread.sleep(5000);
		return age_test;
	}
	
	public boolean checkEmpSalary() throws InterruptedException {
		boolean sal_test;
		logger.info("******** CHECKING EMPLOYEE SALARY ********");
		logger.info("SALARY ==> "+jsonpath.get("data.employee_salary"));
		sal_test = emp_sal.equals(jsonpath.get("data.employee_salary")); 
		if(sal_test) {
			logger.info("********SALARY VALIDATION SUCCESSFUL********");
		} else if (!sal_test) {
			logger.error("********SALARY VALIDATION FAILED ********");
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
		int rownum=XLUtils.getRowCount(path,"GET");
		int colcount=XLUtils.getCellCount(path,"GET",1);		
		String empdata[][]=new String[rownum][colcount];
		
		for(int i=1;i<=rownum;i++) {
			for(int j=0;j<colcount;j++) {
				empdata[i-1][j]=XLUtils.getCellData(path, "GET", i, j);
			}
		}
		return empdata;
	}
	
	@AfterClass
	void tearDown() {
		logger.info("********FINISHED EXECUTION, TEST CASE: TC2_Get_Single_Employee_Data********");
	}
}