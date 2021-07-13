package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//1.Private By locators.
	private By accSections = By.cssSelector("div#content h2");
	private By header = By.cssSelector("div#logo a");
	private By logoutLink = By.xpath("//div[@class='list-group']/a[text()='Logout']");
	//After logging in as we are landing directly in AccPage
	//we are including search related functionality in AccPage
	//itself as though it is applicable for all pages.
	private By searchField = By.name("search");
	private By searchBtn = By.cssSelector("div#search button");
	
	
	//2. Constructor
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	//3. Public actions
	public String getAccPageTitle() {
		return elementUtil.waitForTitle(5, Constants.ACCPAGE_TITLE);
	}
	
	public String getAccPageURL() {
		return elementUtil.getPageUrl();
	}
	
	public String getAccPageHeader() {
		return elementUtil.doGetText(header);
	}
	
	public List<String> getAccountSectionsList() {
		List<String> accSecValueList = new ArrayList<>();
		List<WebElement> accSecList = elementUtil.waitForVisibilityOfElements(accSections, 5);
		
		for(WebElement e : accSecList) {
			accSecValueList.add(e.getText());
		}
		//Sorting this list before returning.
		Collections.sort(accSecValueList);
		return accSecValueList;
	}
	
	public boolean isLogoutExist() {
		return elementUtil.waitForElementVisible(logoutLink, 5).isDisplayed();
	}
	
	//Search method
	
	public SearchResultPage doSearch(String productName) {
		System.out.println("Product name searched for is "+productName);
		elementUtil.doSendKeys(searchField, productName);
		elementUtil.doClick(searchBtn);
		return new SearchResultPage(driver);
	}
}
