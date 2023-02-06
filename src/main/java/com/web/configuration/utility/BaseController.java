 package com.web.configuration.utility;


import java.io.File;
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
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseController extends Utilities{
	
	public static WebDriver driver;
	protected Utilities util = new Utilities();
	public Properties prop;
	public static String fileType = null;

	public BaseController()
	{
		prop= util.readConfigFile();
	}


	WebDriver getdriver() throws Exception {
		try {
			prop= util.readConfigFile();
			if (driver == null)
			{
				switch (prop.getProperty("browser").toLowerCase())
				{
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("disable-inforbars");
				    chromeOptions.addArguments("detach");
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(chromeOptions);
					driver.manage().window().maximize();
					driver.navigate().to(prop.getProperty("web_url"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
					Thread.sleep(10000);
					break;
				case "ie":
					break;
				case "firefox":
					break;
				default :
					ChromeOptions chromeOptions1 = new ChromeOptions();
					chromeOptions1.addArguments("disable-inforbars");
					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver();
					driver.manage().window().maximize();
					driver.navigate().to(prop.getProperty("web_url").toString());
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
				
			}
			return driver;
		}
		catch(Exception e)
		{
			System.out.println ("Unable to create webDriver instance"+e.getMessage());
		}
		return driver;
		
		
	}
	WebElement findElement (By path) throws Exception{
		try {
			this.getdriver();
			
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
			this.getdriver();
			WebElement ele = driver.findElement(path);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	private void updateCommandLineProperty() {
		// TODO Auto-generated method stub

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
