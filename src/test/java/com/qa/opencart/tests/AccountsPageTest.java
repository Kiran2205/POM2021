package com.qa.opencart.tests;

import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.Error;

public class AccountsPageTest extends BaseTest{
	
	//Pre-requisite for a Accounts page test is login page test.
	
	@BeforeClass
	public void accPageSetup() {
		accPage = login.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
//		accPage = new AccountsPage(driver);
	}
	
	@Test
	public void accPageTitleTest() {
		String accPageTitle = accPage.getAccPageTitle();
		Assert.assertEquals(accPageTitle, Constants.ACCPAGE_TITLE, Error.ACCPAGE_TITLE_ERROR);
	}
	
	@Test
	public void accPageHeaderTest() {
		Assert.assertEquals(accPage.getAccPageHeader(), Constants.ACCPAGE_HEADER, Error.ACCPAGE_HEADER_ERROR ); 
	}
	
	@Test
	public void accSectionsListTest() {
		List<String> accountSectionsList = accPage.getAccountSectionsList();
		accountSectionsList.stream().forEach(e -> System.out.println(e));
		Collections.sort(Constants.ACCPAGE_EXPSECLIST);
		Assert.assertEquals(accountSectionsList, Constants.ACCPAGE_EXPSECLIST);
	}
	
	@Test
	public void logoutLinkTest() {
		Assert.assertTrue(accPage.isLogoutExist(), Error.ACCPAGE_LOGOUTLINK_ERROR);
	}
}