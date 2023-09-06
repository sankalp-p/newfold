package com.web.configuration.utility;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginController extends BaseController {
	BaseController base = new BaseController();
	Properties webObject;
	Properties config;
	String filename= "loginpage";
	public LoginController()
	{
	 webObject = util.readWebObjectRepoFile(this.filename);
	 config =util.readConfigFile();
	}
	public By username_field = By.id("LoginUserName");
	public By password_field = By.id("LoginPassword");
	public By login_page_popup = By.xpath("//div[@aria-label='Close Stonly widget']");
	public By tnc_checkbox = By.xpath("//div[contains(text(),\"I agree\")]/preceding-sibling::div/div[2]");
	public By tnc_c_unticked = By.xpath("//div[contains(text(),\"I agree\")]/preceding-sibling::div/div[2]/div");
	public By tnc_link = By.xpath("//div[contains(text(),\"Terms\")]");
	public By tnc_field = By.xpath("//iframe");
	public By tnc_close_button = By.xpath("//div[@aria-label =\"Close dialog\"]/div");
	public By login_button = By.xpath("//div[@id='LoginButton']/div");
	public By forgetpassword_link = By.xpath(" //div[contains(text(),\"Forgot Password?\")]");
	public By username_error = By.xpath("(//*[@id=\"field-react-aria-1-1\"]//div)[3]");
	public By password_error = By.xpath("(//*[@id=\"field-react-aria-1-2\"]//div)[3]");
	public By passwordshow_img = By.xpath("//input[@type='password']/following-sibling::div");
	public By invalidCredentials_popup = By.xpath("//div[contains(text(),'Invalid credentials')]");
	public By username_label = By.xpath("//div[contains(text(),'Email')]/following-sibling::div");
	public By password_label = By.xpath("//div[contains(text(),'Password')]/following-sibling::div");
	
	/* Method to verify user is able to see username and password field on login page */
	public Boolean verifyLoginPageDisplayed() throws Exception

	{
		
		return (base.findElement(webObject.getProperty("username_field")).isDisplayed() && base.findElement(webObject.getProperty("password_field")).isDisplayed());
	}
	
	public Boolean veifyPopUpMessageDisplayed() throws Exception
	{
		
		if (base.findElement(login_page_popup).isEnabled())
		{
			base.moveToElement(base.findElement(login_page_popup));
			return true;
		}
		return false;
	}
	
	/*Method to verify username and password field labels are displayed with mandatory mark*/
	public Boolean verifyLoginFieldlabels() throws Exception
	{
		return (base.findElement(username_label).isDisplayed() && base.findElement(password_label).isDisplayed());
	}
	
	/* Method to verify that user is able to enter the username*/
	public Boolean enterUserName(String username) throws Exception
	{
		WebElement usernme = base.findElement(username_field);
		if (usernme.isDisplayed())
		{	
			usernme.clear();
			usernme.sendKeys(username);
			return usernme.getAttribute("value").contentEquals(username);
		}
		return false;
	}
	
	/* Method to verify that user is able to enter password in passowrd field*/
	public Boolean enterPassword(String password) throws Exception
	{
		WebElement pwd = base.findElement(password_field);
		if (pwd.isDisplayed())
		{	
			pwd.clear();
			pwd.sendKeys(password);
			return pwd.getAttribute("value").contentEquals(password);
		}
		return false;
	}
	
	/* Method to verify that login button is disabled if user name , password fields are empty */
	public Boolean disableLoginButton() throws Exception
	{
		base.findElement(username_field).clear();
		base.findElement(password_field).clear();
		return base.findElement(login_button).isEnabled();
	}
	
	/* Method to verify that customre can accept terms and conditions */
	public Boolean tncnotClicked() throws Exception
	{
		WebElement tnc = base.findElement(tnc_checkbox);
		if (!base.isDisplayed(tnc_c_unticked))
		{
			tnc.click();
			tnc.click();
			return true;	
		}
		return false;
	}
	
	/* Method to verify that tnc link is enabled and open tncs*/
	public Boolean clickTncLink() throws Exception
	{
		base.findElement(tnc_link).click();
		if (base.findElement(tnc_field).isDisplayed())
		{
			base.findElement(tnc_close_button).click();
			return true;
		}
		return false;
	}
	
	/* Method to click login button when username and password are entered*/
	public Boolean clickLoginButton() throws Exception
	{
		WebElement loginbutton = base.findElement(login_button);
		if (loginbutton.isEnabled())
		{
			loginbutton.click();
			return true;
		}
		return false;
	}
	
	public Boolean invalidCredentialsPopup() throws Exception
	{
		WebElement loginbutton = base.findElement(login_button);
		int i=5;
		if (loginbutton.isEnabled())
		{
			base.scrollIntoElement(loginbutton);
			for (int j=0;j<i;j++)
			{
				base.jsClick(loginbutton);
			if (base.isDisplayed(invalidCredentials_popup))
			return true;
			}
			return false;
		}
		return false;
	
	}

	public boolean clickRegistrationlink() throws Exception {
		// TODO Auto-generated method stub
		try
		{base.findElement(webObject.getProperty("register_link")).click();
		return true;
		}
		catch(Exception e)		{
			return false;
		}
	}

	

	public boolean fillandSubmitRegistrationForme(TestData data) {
		// TODO Auto-generated method stub
		
		try {
			base.findElement(webObject.getProperty("firstName_field")).sendKeys(data.getFirstName());
			base.findElement(webObject.getProperty("lastName_field")).sendKeys(data.getLastName());
			base.findElement(webObject.getProperty("address_field")).sendKeys(data.getAddress());
			base.findElement(webObject.getProperty("city_field")).sendKeys(data.getCity());
			base.findElement(webObject.getProperty("state_field")).sendKeys(data.getState());
			base.findElement(webObject.getProperty("zipCode_field")).sendKeys(data.getZipCode());
			base.findElement(webObject.getProperty("phone_field")).sendKeys(data.getPhone());
			base.findElement(webObject.getProperty("ssn_field")).sendKeys(data.SSN);
			base.findElement(webObject.getProperty("usernamefield")).sendKeys(data.Username);
			base.findElement(webObject.getProperty("passwordfield")).sendKeys(data.Password);
			base.findElement(webObject.getProperty("confirm_field")).sendKeys(data.Password);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}

	
}
