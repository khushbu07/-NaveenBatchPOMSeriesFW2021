package com.qa.opencart.tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

public class AccountsPageTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accpgeTitleTest() {
		String title = accPage.getAccPageTitle(); 
		System.out.println("Account page title is " + title);
		Assert.assertEquals(title, Constants.ACC_PAGE_TITLE);
	}

	@Test
	public void accPageLogoutLinkExist() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}

	@Test
	public void accpageSearchTest() {
		Assert.assertTrue(accPage.isSeachFieldExist());

	}

	@Test
	public void accPageSecHeaderTest() {
		List<String> actList = accPage.getAccountSecList();
		System.out.println(actList);
		Assert.assertEquals(actList, Constants.EXP_ACCOUNTS_SECTIONS_LIST);

	}
	
	@DataProvider
	public Object[][] productData(){
		return new Object[][]{
			{"macbook"},
			{"iMac"},
			{"Apple"}
			
		}; 
	}

	@Test(dataProvider="productData")
	public void doSearchTest(String productName){
		resultPage=accPage.doSearch(productName);
		Assert.assertTrue(resultPage.getSearchProductListCount()>0);
	}
	
	@DataProvider
	public Object[][] productSelectData(){
		return new Object[][]{
			{"macbook","Macbok pro"},
			{"iMac","iMac"},
			{"Apple","Apple Cinema 30\""}
			
		}; 
	}

	
	@Test(dataProvider="productSelectData")
	public void selectPrductTest(String productName,String mainProductName){
		resultPage=accPage.doSearch(productName);
		resultPage.selectProduct(mainProductName);
	}
}
