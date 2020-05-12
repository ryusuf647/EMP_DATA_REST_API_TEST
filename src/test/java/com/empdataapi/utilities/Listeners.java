package com.empdataapi.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@SuppressWarnings("deprecation")
public class Listeners extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	public void onStart(ITestContext testContext) {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/empdataReport.html");
		htmlReporter.config().setDocumentTitle("REST API - EMPLOYEE DATA REPORT");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project Name", "Employee Database API");
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("user", "test_user");		
	}
	
	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		
		test.log(Status.PASS, "Test Case " + result.getName() + " PASSED");
	}
	
	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		
		test.log(Status.FAIL, "Test Case " + result.getName() + " FAILED");
		test.log(Status.FAIL, "Test Case " + result.getThrowable() + " FAILED");
	}
	
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test Case " + result.getName() + " SKIPPED");
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
	}
}