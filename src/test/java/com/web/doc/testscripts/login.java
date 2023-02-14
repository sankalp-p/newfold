package com.web.doc.testscripts;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;
import com.web.configuration.utility.BaseController;
import com.web.configuration.utility.BaseTestScripts;
import com.web.configuration.utility.LoginController;

public class login extends BaseTestScripts{
	
	LoginController login = new LoginController();
	BaseController base = new BaseController();
	
	@Test(description ="Verify that login page is displayed with all details")
	public void TC_001_doc_web_LoginPage()
	{
		try {
			
			test = reports.startTest("Verify that login page is displayed with all details");
			stepName = "Verfiy login page is displayed";
			assertTrue(login.verifyLoginPageDisplayed(),"Failed to"+stepName);
			logger();
			
			stepName= "Verify login page displays with add popup";
			assertTrue(login.veifyPopUpMessageDisplayed(),"Failed to"+stepName);
			logger();
			
			stepName= "Verify that username and password labels are displayed with mandatory mark";
			assertTrue(login.verifyLoginFieldlabels(),"Failed to"+stepName);
			logger();
			
			stepName="Verify that login button is disabled by default";
			assertTrue(login.disableLoginButton(),"Failed to"+stepName);
			logger();
			
			stepName="Verify tnc checkbox is working";
			assertTrue(login.tncnotClicked(),"Failed to"+stepName);
			logger();
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	@Test(description= "Veify that customer get error message with invalid credentials login")
	public void TC_002_doc_web_invalidLogin() throws Exception
	{
		try {
			test = reports.startTest("Veify that customer get error message with invalid credentials login");
//			stepName= "Verify login page displays add popup";
//			assertTrue(login.veifyPopUpMessageDisplayed(),"Failed to"+stepName);
//			logger();
			
			stepName = "Verify user can enter username";
			assertTrue(login.enterUserName(base.prop.getProperty("invalid_username")),"Failed to"+stepName);
			logger();
			
			stepName = "Verify user can enter password";
			assertTrue(login.enterPassword(base.prop.getProperty("invalid_pasword")),"Failed to"+stepName);
			logger();
			
			stepName="Verify invalid credentials popup is displayed";
			assertTrue(login.invalidCredentialsPopup(),"Failed to"+stepName);
			logger();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
		

}
