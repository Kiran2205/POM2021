package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationPageTest extends BaseTest{
	
	@BeforeClass
	public void regPageSetup() {
		regPage = login.clickRegisterLink();
	}
	
	@DataProvider
	public Object[][] getRegisterData() {
		Object[][] regData = ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
		return regData;
	}
	
	public static String createRandomEmail() {
		Random rand = new Random();
		String email = "testautomation"+rand.nextInt(10000)+"@gmail.com";
		return email;
	}
	
	//Here while writing holding parameter for test method, we should maintain
	//same sequence as that of the column names in the excel.
	@Test(dataProvider = "getRegisterData")
	public void userRegistration(String firstName, String lastName, String telephone, String password, String subscribe ) {
		boolean isRegistered = regPage.accountRegistration(firstName, lastName, createRandomEmail(), telephone, password, subscribe);
		Assert.assertTrue(isRegistered);
	}
	
}
