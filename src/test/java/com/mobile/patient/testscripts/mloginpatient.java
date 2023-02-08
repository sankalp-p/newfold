package com.mobile.patient.testscripts;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.mobile.automation.MBaseController;
import com.mobile.automation.MBaseTestScripts;
import com.mobile.automation.MLoginController;

public class mloginpatient extends MBaseTestScripts{
	
	MLoginController login = new MLoginController();
	MBaseController base = new MBaseController();
	@Test(description ="Verify that login page is displayed with all details")
	public void TC_001_patient_mobile_LoginPage()
	{
		try {
			
			test = reports.startTest("Verify that user is able to navigate to login page");
			stepName = "Verfiy login page is displayed on app";
			assertTrue(login.verifyLandingPageDisplayed(),"Failed to"+stepName);
			logger();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	
	@Test(description = "Verify that user is not able to login with invalid credentials")
	public void TC_002_patient_mobile_invalidLogin()
	{
		try {
			test = reports.startTest("Verify that user is not able to login with invalid credentials");
			stepName= "Verify customer is able to enter username and password and click login";
			assertTrue(login.verifyUnabletoLogin(),"Failed to "+ stepName);
			logger();
		} catch (Exception e) {
		
			System.out.println(e.getMessage());
		}
	}
}
