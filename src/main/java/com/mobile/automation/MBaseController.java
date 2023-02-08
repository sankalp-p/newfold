 package com.mobile.automation;


import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;

public class MBaseController extends MUtilities{
	
	public static WebDriver driver;
	protected MUtilities util = new MUtilities();
	public Properties prop;
	public static String fileType = null;

	public MBaseController()
	{
		prop= util.readConfigFile();
	}

	WebDriver getmobiledriver() throws Exception
	{
		try {
			if (driver == null)
			{
			if (prop.getProperty("mobile").contentEquals("android"))
			{
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.0");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "R5CR30DBFGW");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
				capabilities.setCapability("appPackage", "com.foldhealth.patient");
				capabilities.setCapability("appActivity", "com.patientapp.MainActivity");
				capabilities.setCapability("uiautomator2ServerInstallTimeout", 2000000);
				
				driver = new AndroidDriver (new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
				Thread.sleep(10000);
				return driver;
			}
			}
		}
		catch(Exception e)
		{
			throw new Exception ("Error while instatiating mobile driver",e);
		}
		
		return driver;
	}

	
	
	WebElement findElement (By path) throws Exception{
		try {
			this.getmobiledriver();
			
			if (driver== null)
				throw new Exception ("WebDriver instance is not created");
			else
			{
				WebElement ele = driver.findElement(path);
				return ele;
			}
		}
		catch(Exception e) {
			throw new Exception ("Unable to find element"+ e.getMessage());
		}
	}

	List<WebElement> findElements (By path) throws Exception{ 

		List<WebElement> eles = null;

		try {
			eles= driver.findElements(path);

			return eles;
		}
		catch(Exception e)
		{
			throw new Exception ("Unable to find elements"+ e.getMessage());
		}
	}
	
	Boolean isDisplayed(By path) throws Exception
	{
		try {
			this.getmobiledriver();
			WebElement ele = driver.findElement(path);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	public static  String capture ( String screenShotName) throws Exception{
		try
		{
			TakesScreenshot  t = (TakesScreenshot) driver;
			File source = t.getScreenshotAs(OutputType.FILE);
			File logDir = new File(System.getProperty("user.dir")+"//screenshots//");
			if (!logDir.exists())
				logDir.mkdir();
			String destination = System.getProperty("user.dir")+"//screenshot//"+screenShotName+".png";
			File dest = new File(destination);
			FileUtils.copyFile(source, dest);
			return destination;
			
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		return null;
		
	}
	
	public String logger(String stepname)
	{
		try {
			return this.capture( stepname);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void scrollIntoElement(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void jsClick(WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}
	
	public void moveToElement(WebElement element)
	{
		 Actions actions = new Actions(driver);  
		   actions.moveToElement(element); 
		   actions.clickAndHold();  
		   actions.release().perform(); 
	}

	

}
