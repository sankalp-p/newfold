package com.web.configuration.utility;


import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
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

	AppiumDriver getmobiledriver()
	{
		try {
			if (prop.getProperty("mobile").contentEquals("android"))
			{
				DesiredCapabilities capabilities = new DesiredCapabilities();
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.0");
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "R5CR30DBFGW");
				capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");
				capabilities.setCapability(MobileCapabilityType.APP, "com.foldhealth.patient");
			}
		}
		catch(Exception e)
		{

		}
		return null;

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
					chromeOptions.addArguments("--remote-allow-origins=*");
					WebDriverManager.chromedriver().setup();
					//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/chromedriver.exe");
					driver = new ChromeDriver(chromeOptions);
					driver.manage().window().maximize();
					driver.navigate().to(prop.getProperty("web_url"));
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
					((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
					
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

	WebElement findElement (String path) throws Exception{

		try {
			String[] combined = path.split("\\|");
			String loc_type = combined[0];
			String loc_string = combined[1];
			WebElement element = null;
			this.getdriver();
			//Issues with dependencies try to uncommment below code and run
			//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5,0));

			switch(loc_type.toLowerCase())
			{
			case "xpath":
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(loc_string)));
				element= driver.findElement(By.xpath(loc_string));
				break;
				
			case "id":
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.id(loc_string)));
				element= driver.findElement(By.id(loc_string));
				break;
				
			case "name":
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.name(loc_string)));
				element= driver.findElement(By.name(loc_string));
				break;
				
			case "css":
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(loc_string)));
				element= driver.findElement(By.cssSelector(loc_string));
				break;
				
			case "linktext":
				//wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(loc_string)));
				element= driver.findElement(By.linkText(loc_string));
				break;
			
				
			}
			return element;
		}
			catch(Exception e)
			{
				throw new Exception("Unable to find element due to "+e.toString());
			}
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
