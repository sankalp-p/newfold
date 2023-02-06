package com.web.doc.testscripts;

import java.io.File;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.web.configuration.utility.BaseController;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;


public class listnerclass implements ITestListener  {
	BaseController base = new BaseController();
	final Logger log = Logger.getLogger(Log.class.getName());
	String testcaseName ;
	ExtentReports reports;
	ExtentTest test;
	@Override
	public void onTestStart(ITestResult result) {
		
		//testcaseName=result.getMethod().getDescription();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.PASS, result.getTestName(), "PASSED");
		//test.log(LogStatus.PASS, result.getTestName()+test.addScreenCapture(base.logger( result.getMethod().getDescription())), "PASSED");
		log.debug("*******Test Passed*******"+result.getTestName());
		reports.endTest(test);
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
			test.log(LogStatus.FAIL, result.getTestName(), "FAILED");
			//test.log(LogStatus.FAIL, result.getTestName()+test.addScreenCapture(base.logger( result.getMethod().getDescription())), "FAILED");
			log.debug("*******Test Failed*******"+result.getTestName());
			reports.endTest(test);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(LogStatus.SKIP, result.getTestName(), "SKIPPED");
		log.error("*******Test Skipped*******"+result.getTestName()+result.getThrowable());
		reports.endTest(test);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		File newfolder = new File (System.getProperty("user.dir")+"//Execution_report");
		   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-ddHH-mm-ss");  
		   LocalDateTime now = LocalDateTime.now();     
		try {
			if (!newfolder.exists())
			{
				newfolder.mkdir();
				log.debug("Execution Report folder has been created");
			}
			else 
				log.debug("Execution Report exists");
			reports = new ExtentReports(System.getProperty("user.dir")+"\\Execution_report\\ExtentReportResults"+dtf.format(now)+".html", false);
			reports.addSystemInfo("Host Name","Fold").addSystemInfo("Environment","Test5").addSystemInfo("Tester","Sankalp");
			reports.loadConfig(new File(System.getProperty("user.dir")+"/src/main/java/com/web/configuration/extent-config.xml"));

		}
		catch (Exception e)
		{
			log.error(e.getMessage());
		}

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		reports.flush();
		reports.close();
		base.driver.quit();
		
	}
	
	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(Method method)
	{
		testcaseName= method.getName();
	}
}
