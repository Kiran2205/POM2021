package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil elementUtil;
	
	//By locators
	private By prodHeader = By.cssSelector("div#content h1");
	private By prodImages = By.cssSelector("ul.thumbnails li img");
	private By prodMetadata = By.cssSelector("div#content ul.list-unstyled:nth-of-type(1) li");
	private By prodPricedata = By.cssSelector("div#content ul.list-unstyled:nth-of-type(2) li");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By successMsg = By.cssSelector("div.alert.alert-success.alert-dismissible");
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}
	
	public String getProdHeaderText() {
		return elementUtil.doGetText(prodHeader);
	}
	
	public int getProdImagesCount() {
		return elementUtil.getElements(prodImages).size();
	}
	
	/**
	 * This method will collect product metadata and price data as HashMap
	 * @return 
	 * @return productInfoMap
	 */
	public Map<String, String> getProdDetails() {
		Map<String, String> productInfoMap = new HashMap<String, String>();
		productInfoMap.put("Name", getProdHeaderText());
		
		//Product Metadata
		List<WebElement> metadataList = elementUtil.getElements(prodMetadata);
		System.out.println("Total metadata information for product is "+metadataList.size());
		
		for(WebElement e : metadataList) {
			//Brand : Apple
			String[] meta = e.getText().split(":");
			String metaKey = meta[0].trim();
			String metaValue = meta[1].trim();
			
			productInfoMap.put(metaKey, metaValue);
			
		}
		
		//Price Data
		List<WebElement> priceDetailList = elementUtil.getElements(prodPricedata);
		String priceValue = priceDetailList.get(0).getText().trim();
		String exTaxPriceValue = priceDetailList.get(1).getText().trim();
		
		productInfoMap.put("Price", priceValue);
		productInfoMap.put("ExTaxPrice", exTaxPriceValue);
		
		return productInfoMap;
	}
	
	public void addQuantity(String quantity) {
		elementUtil.doSendKeys(this.quantity, quantity);
	}
	
	public void addToCart() {
		elementUtil.doClick(addToCartBtn);
	}

	public String getSuccessMsg() {
		return elementUtil.waitForElementVisible(successMsg, 5).getText().trim();
	}
}
