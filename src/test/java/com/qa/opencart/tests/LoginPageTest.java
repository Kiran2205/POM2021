package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//These allure annotations are used to link story and epic from jira
//Agile methodology 
@Epic("Epic 500 : Desgin login page for demo cart application")
@Story(" US 100 : Develop a feature  with all login page scenarios")
public class LoginPageTest extends BaseTest{
//	//we need to get a driver instance to open browser.
//	//Thus creating a reference and object of DriverFactory
//
//	DriverFactory df;
//	private WebDriver driver;
//	
//	LoginPage login;
//	
//	@BeforeTest
//	public void setUp() {
//		df = new DriverFactory();
//		driver = df.init_driver("Chrome");
//		login = new LoginPage(driver);
//	}
	
	@Description("This test is used to check whether Login Page title is correct or not")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String title = login.getLoginPageTitle();
		System.out.println("Login page title is "+title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Description("This test is used to check whether Login Page URL is correct or not")
	@Severity(SeverityLevel.MINOR)
	@Test(priority = 2, enabled = true)
	public void loginPageURLTest() {
		String  pageURL = login.getLoginPageURL();
		Assert.assertTrue(pageURL.contains(Constants.LOGIN_PAGE_URLVALUE));
	}
	
	@Description("This test is used to check whether Forgot Password link is available in Login Page or not")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 3)
	public void forgotPwdLinkTest() {
		Assert.assertTrue(login.isForgotPwdLinkExist());
	}
	
	@Description("This test is used to check Login Functionality for Opencart application")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 4)
	public void loginTest() {
		login.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
	}
	
	@DataProvider
	public Object[][] loginNegativeData() {
		return new Object[][] {{"test@gmail.com","test123"},{" ","test235"},{"test23@gmail.com"," "}};
	}
	
	@Test(priority = 0, dataProvider = "loginNegativeData")
	public void loginNegativeTest(String userName, String password) {
		Assert.assertTrue(login.doLoginNegativeScenario(userName, password));
	}
	
//	@AfterTest
//	public void tearDown() {
//		driver.quit();
//	}

}
