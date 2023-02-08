package com.mobile.automation;

import org.openqa.selenium.By;

import io.appium.java_client.AppiumBy;

public class MLoginController {
	MBaseController base = new MBaseController();
	
	public By landingPageNext_button = By.xpath("//android.widget.TextView[@text='Next']");
	public By landingPageDone_button = By.xpath("//android.widget.TextView[@text='Done']");
	public By loginPageusername_field = By.xpath("//android.widget.EditText[@text='Email / Mobile number']");
	public By loginPassword_field = By.xpath("//android.widget.EditText[@text='Password']");
	public By tnc_radio = By.xpath("//android.widget.CheckBox/android.view.ViewGroup[2]/android.view.ViewGroup");
	public By login_button = By.xpath("//android.widget.TextView[@text='Done']");
	public By forgotPassword_link = By.xpath("//android.widget.TextView[@text='Forgot Password?']");
	
	public Boolean verifyLandingPageDisplayed() throws Exception
	
	{
		if (base.isDisplayed(landingPageNext_button))
		{
			base.findElement(landingPageNext_button).click();
			Thread.sleep(2000);
			base.findElement(landingPageNext_button).click();
			Thread.sleep(2000);
			base.findElement(landingPageDone_button).click();
		}
		if (base.isDisplayed(loginPageusername_field))
		 return true;
		else
			return false;
		
	}
	
	public Boolean verifyUnabletoLogin() throws Exception
	{
		if (base.isDisplayed(loginPageusername_field))
		{
			base.findElement(loginPageusername_field).sendKeys("dummy");
			base.findElement(loginPassword_field).sendKeys("******");
			base.findElement(tnc_radio).click();
			base.findElement(login_button).click();
			return true;
		}
		else
			return false;
	}
	
}
