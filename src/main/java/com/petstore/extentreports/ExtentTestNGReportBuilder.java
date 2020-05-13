package com.petstore.extentreports;

import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.petstore.utilities.Constants;

public class ExtentTestNGReportBuilder {

	private static ExtentReports extent;
    @SuppressWarnings("rawtypes")
	private static ThreadLocal parentTest = new ThreadLocal();
    @SuppressWarnings("rawtypes")
	private static ThreadLocal test = new ThreadLocal();
    private ExtentTest reportLoggerClass;
    private ExtentTest reportLoggerTest;
    private String reportsLocation= Constants.PROJECTPATH+"/reports/";

	@BeforeSuite
	public void beforeSuite() {
		extent = ExtentManager.createInstance(reportsLocation+"reports.html");
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportsLocation+"reports.html");
		extent.attachReporter(htmlReporter);
		
		
	}
	
    @BeforeClass
    public synchronized void beforeClass() {
        ExtentTest parent = extent.createTest(getClass().getName());
        parentTest.set(parent);
        reportLoggerClass=parent;
    }

    @BeforeMethod
    public synchronized void beforeMethod(Method method) {
        ExtentTest child = ((ExtentTest) parentTest.get()).createNode(method.getName());
        test.set(child);
        reportLoggerTest=child;
    }

    @AfterMethod
    public synchronized void afterMethod(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE)
            ((ExtentTest) test.get()).fail(result.getThrowable());
        else if (result.getStatus() == ITestResult.SKIP)
            ((ExtentTest) test.get()).skip(result.getThrowable());
        else
            ((ExtentTest) test.get()).pass("Test passed");
        
        extent.flush();
    }
    
    public ExtentTest getReportLoggerClass(){
    	return reportLoggerClass;
    }

    public ExtentTest getReportLoggerTest(){
    	return reportLoggerTest;
    }

}
