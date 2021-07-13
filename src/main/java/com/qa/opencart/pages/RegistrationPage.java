package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.JavaScriptUtil;

public class RegistrationPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	//Js util is declared to make use of js methods when driver api
	//methods are not working.
	private JavaScriptUtil jsUtil;
	
	//By locators
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");
	private By agreeChechkbox = By.xpath("//input[@type='checkbox'][@name='agree']");
	private By continueBtn = By.cssSelector("input.btn.btn-primary");
	private By successMsg = By.cssSelector("div#content h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
		jsUtil = new JavaScriptUtil(driver);
		
	}
	
	public boolean accountRegistration(String firstName, String lastName, String email, String telephone, String password, String subscribe) {
		elementUtil.doSendKeys(this.firstName, firstName);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.confirmPassword, password);
		if(subscribe.equalsIgnoreCase("yes")) {
			elementUtil.doClick(subscribeYes);
		}else {
			elementUtil.doClick(subscribeNo);
		}
		elementUtil.doClick(agreeChechkbox);
		elementUtil.doClick(continueBtn);
		String regText = elementUtil.waitForElementVisible(successMsg, 10).getText();
		System.out.println("Account creation : "+regText);
		if(regText.contains(Constants.REGISTRATION_MSG)) {
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			return true;
		}
		return false;
	}
	
}
