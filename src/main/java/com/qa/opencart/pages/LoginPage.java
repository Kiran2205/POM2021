package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	//For page chaining purpose.
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//1. Private By locators
	private By userName = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@type='submit']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[text()='Forgotten Password']");
	private By registerLink = By.linkText("Register");
	private By loginErrorMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
	
	//2. constructor
	public LoginPage(WebDriver driver) {
		//local Login Page driver is initialised for page chaining.
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	//3. public page actions (methods)
	@Step("This test method is to get login page title")
	public String getLoginPageTitle() {
//		return driver.getTitle();
		return elementUtil.waitForTitle(5, Constants.LOGIN_PAGE_TITLE);
	}
	
	@Step("This test method is to get login page url")
	public String getLoginPageURL() {
//		return driver.getCurrentUrl();
		return elementUtil.getPageUrl();
	}
	
	@Step("This test method is to verify forgot password link")
	public boolean isForgotPwdLinkExist() {
//		return driver.findElement(forgotPwdLink).isDisplayed();
		return elementUtil.doIsDisplayed(forgotPwdLink);
	}
	
	@Step("This test method is to verify login functionality for open cart application with username : {0} and password : {1}")
	public AccountsPage doLogin(String userName, String password) {
//		driver.findElement(this.userName).sendKeys(userName);
		elementUtil.doSendKeys(this.userName, userName);
//		driver.findElement(this.password).sendKeys(password);
		elementUtil.doSendKeys(this.password, password);
//		driver.findElement(this.loginBtn).click();
		elementUtil.doClick(this.loginBtn);
		return new AccountsPage(this.driver);
	}
	
	@Step("This test method is to verify login functionality for open cart application with username : {0} and password : {1}")
	public boolean doLoginNegativeScenario(String userName, String password) {
//		driver.findElement(this.userName).sendKeys(userName);
		elementUtil.doSendKeys(this.userName, userName);
//		driver.findElement(this.password).sendKeys(password);
		elementUtil.doSendKeys(this.password, password);
//		driver.findElement(this.loginBtn).click();
		elementUtil.doClick(this.loginBtn);
		return elementUtil.doIsDisplayed(loginErrorMsg);
	}
	
	@Step("This test method is to click register link")
	//This method is to enable User Registration by clicking Register link.
	public RegistrationPage clickRegisterLink() {
		elementUtil.doClick(registerLink);
		return new RegistrationPage(driver);
	}
}
