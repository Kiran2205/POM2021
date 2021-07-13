package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.listeners.TestAllureListener;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.pages.SearchResultPage;

//Adding @Listener, if in case attachments are missing in allure report
//even after adding listener tag in testng.xml
@Listeners(TestAllureListener.class)
public class BaseTest {
	//we need to get a driver instance to open browser.
	//Thus creating a reference and object of DriverFactory
	
	DriverFactory df;
	//Keeping access modifier as public in order to make use in PageTest class
	//for the instantiation of corresponding Page class.
	public WebDriver driver;
	public Properties prop;
	
	public LoginPage login;
	public AccountsPage accPage;
	public SearchResultPage searchResPage;
	public ProductInfoPage prodInfoPage;
	public RegistrationPage regPage;
	
	//Reading browser parameter from testNG.xml property
	@Parameters({"browser"})
	@BeforeTest
	//Creating a holding parameter for browser value read from testng.xml
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop = df.init_prop();
		//Making use of set method of Properties class to set value at run time.
		prop.setProperty("browser", browserName);
		driver = df.init_driver(prop);
		login = new LoginPage(driver);
	}
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
