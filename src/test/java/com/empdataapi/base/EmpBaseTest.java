package com.empdataapi.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EmpBaseTest {
	
	public static RequestSpecification httpRequest;
	public static Response response;
	public Logger logger;
	public final String recNotFound = "Record does not found.";
	
	public String emp_id;
	public String emp_name;
	public String emp_age;
	public String emp_sal;
	public String resp_statcode;
	public String resp_statline;
	public String resp_conttype;
	public String resp_sertype;
	public String resp_contlen;
	public final String url = "http://dummy.restapiexample.com/api/v1";
	public JsonPath jsonpath;	
	
	@BeforeClass
	public void setup() {
		logger = Logger.getLogger("EmpDataRestAPI");
		PropertyConfigurator.configure("log4j.properties");
		logger.setLevel(Level.DEBUG);
	}
}