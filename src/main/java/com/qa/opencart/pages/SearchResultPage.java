package com.qa.opencart.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class SearchResultPage {
	
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//By locators
	private By searchItemResult = By.cssSelector("div.product-thumb");
	private By resultItems = By.cssSelector("div.product-thumb h4 a");
	
	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	public int getProductResultCount() {
		return elementUtil.getElements(searchItemResult).size();
	}
	
	public ProductInfoPage selectProductFromList(String productName) {
		List<WebElement> resEleList = elementUtil.getElements(resultItems);
		System.out.println("Total result size of product " + productName + " is : " + resEleList.size());
		for (WebElement e : resEleList) {
			if (e.getText().trim().equalsIgnoreCase(productName.trim())) {
				e.click();
				break;
			}
		}
		return new ProductInfoPage(driver);
	}

}
