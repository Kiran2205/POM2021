package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.base.BaseTest;

public class ProductInfoTest extends BaseTest{
	
	SoftAssert softAssert = new SoftAssert();
	
	@BeforeClass
	public void prodInfoSetUp() {
		accPage = login.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	//Parameterising data without using excel
	@DataProvider
	public Object[][] searchData() {
		return new Object[][] {{"Macbook"},{"iMac"},{"iPhone"}};
	}
	
	//Using data, by having data provider property and creating holding parameter
	@Test(dataProvider = "searchData")
	public void prodCountTest(String productName) {
		searchResPage  = accPage.doSearch(productName);
		Assert.assertTrue(searchResPage.getProductResultCount() > 0);
	}
	
	@Test
	public void prodInfoHeaderTest() {
		searchResPage = accPage.doSearch("iMac");
		prodInfoPage = searchResPage.selectProductFromList("iMac");
		Assert.assertEquals(prodInfoPage.getProdHeaderText().trim(), "iMac");
	}
	
	@Test
	public void prodImgTest() {
		searchResPage = accPage.doSearch("Macbook");
		prodInfoPage = searchResPage.selectProductFromList("MacBook Pro");
		Assert.assertTrue(prodInfoPage.getProdImagesCount()== 4);
	}
	
	@Test
	public void getProdInfoTest() {
		searchResPage = accPage.doSearch("Macbook");
		prodInfoPage = searchResPage.selectProductFromList("MacBook Pro");
		Map<String, String> prodDetails = prodInfoPage.getProdDetails();
		prodDetails.forEach((k,v)-> System.out.println(k+" : "+v));
		
		//Here we are introducing soft assertion so that test will not be failed
		//Just cz of one wrong value.
		//Doing softAssert's assertAll() is must, to check how many are pass
		softAssert.assertEquals(prodDetails.get("Name"), "MacBook Pro");
		softAssert.assertEquals(prodDetails.get("Brand"), "Apple");
		softAssert.assertEquals(prodDetails.get("Product Code"), "Product 18");
		softAssert.assertEquals(prodDetails.get("Reward Points"), "800");
		softAssert.assertEquals(prodDetails.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(prodDetails.get("Price"), "$2,000.00");
		softAssert.assertEquals(prodDetails.get("ExTaxPrice"), "Ex Tax: $2,000.00");
		
		softAssert.assertAll();
	}
	
	@Test
	public void addToCart() {
		searchResPage = accPage.doSearch("Macbook");
		prodInfoPage = searchResPage.selectProductFromList("MacBook Pro");
		prodInfoPage.addQuantity("2");
		prodInfoPage.addToCart();
		Assert.assertTrue(prodInfoPage.getSuccessMsg().contains("Success"));
	}
}
