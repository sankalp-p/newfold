package com.mobile.automation;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class MBaseTestScripts extends MBaseController{

	 Logger log = Logger.getLogger(Log.class.getName());
	 protected String testcaseName = null;
	 protected ExtentReports reports;
	 protected ExtentTest test;
	 protected String result = "PASS";
	 protected String stepName ="";
	 protected static String testName = null;
	 public static WebDriver driver = null;
	 protected int datasetNum =0;
	 
	@BeforeSuite
	public void createReportFolder()
	{
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
		}
		catch(Exception e)
		{
			log.warn("Unable to create Execution Report folder");
		}
	}
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest() throws Exception
	{
		try {
			setDatasetNum();
			testcaseName = this.getClass().getSimpleName();
			File newfolder = new File (System.getProperty("user.dir")+"//Execution_report");
			   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-ddHH-mm-ss");  
			   LocalDateTime now = LocalDateTime.now();     
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
		catch(Exception e)
		{
			test.log(LogStatus.valueOf(result), stepName);
		}
	}
	
	@BeforeMethod(alwaysRun =true)
	public void beforeMethod(Method method)
	{
		try
		{
			testName= method.getName();
		}
		catch(Exception e)
		{
			log.warn(e);
		}
	}
	@AfterTest
	public void afterTest() throws InterruptedException, IOException{
		resetDatasetNum();
		reports.flush();
	}
	
	@AfterMethod
	public void getResult (ITestResult result) throws Exception
	{
		incrementDatasetnum();
		if(result.getStatus()==ITestResult.FAILURE)
		{
			test.log(LogStatus.FAIL,"Test Case"+result.getName()+result.getThrowable());
			log.warn(stepName+" FAILED ");
		}
		else if (result.getStatus() == ITestResult.SKIP)
		{
			test.log(LogStatus.SKIP,"Test Case"+result.getName()+result.getThrowable());
			log.warn(stepName+" SKIPPED ");
		}
		reports.endTest(test);
	}
	
	@AfterSuite
	public void afterSuite()
	{
		if(driver != null)
			driver.quit();
		resetDatasetNum();
		reports.flush();
		reports.close();
		
	}
	
	public void logException( String stepName, String message, String testcaseName)
	{
		result= "FAIL";
		try
		{
			String screenShotPath = MBaseController.capture(testcaseName+"-"+datasetNum);
			test.log(LogStatus.FAIL,test.addScreenCapture(screenShotPath));
		}
		catch(Exception e)
		{
			test.log(LogStatus.valueOf(result),datasetNum+":"+ stepName+""+e.getMessage());
			log.warn(e);
		}
	}
	
	public void logger()
	{
		String screenShotPath;
		try
		{
			screenShotPath = MBaseController.capture(stepName+"-"+datasetNum);
			test.log(LogStatus.PASS,stepName+test.addScreenCapture(screenShotPath));
		}
		catch(Exception e)
		{
			log.warn(e);
		}
	}
	
	public void setDatasetNum()
	{
		datasetNum =1;
	}
	
	public void resetDatasetNum()
	{
		datasetNum=0;
	}
	public void incrementDatasetnum()
	{
		datasetNum++;
	}
}




