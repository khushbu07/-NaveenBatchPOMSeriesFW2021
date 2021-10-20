

package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.Constants;

public class ProductInfoTest extends BaseTest {

	@BeforeClass
	public void accPageSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void productHeaderTest() {
		resultPage = accPage.doSearch("macbook");
		productInfoPage = resultPage.selectProduct("MacBook Pro");
		String actHeader = productInfoPage.getProductHeaderText();
		Assert.assertEquals(actHeader, "MacBook Pro");
	}

	@DataProvider
	public Object[][] getImageData() {
		return new Object[][] { { "macbook", "MacBook Pro", 4 }, { "iMac", "iMac", 3 },
				{ "Apple", "Apple Cinema 30\"", 6 } };
	}

	@Test(dataProvider = "getImageData")
	public void productImageCountTest(String productName, String mainProductName, int imageCount) {
		resultPage = accPage.doSearch(productName);
		productInfoPage = resultPage.selectProduct(mainProductName);
		Assert.assertEquals(productInfoPage.getProductImagesCount(), imageCount);
	}
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	@Test
	public void productMetaListTest() {

		resultPage = accPage.doSearch("macbook");
		
		System.out.println("productMetaListTest running....by me");
		productInfoPage = resultPage.selectProduct("MacBook Pro");
		Map<String,String> actProdMap=productInfoPage.getProductMetaData();
		actProdMap.forEach((k,v)->System.out.println(k +":" + v));
		softAssert.assertEquals(actProdMap.get("productname"), "MacBook Pro");
		softAssert.assertEquals(actProdMap.get("Brand"), "Apple");
		softAssert.assertEquals(actProdMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actProdMap.get("price"), "$2,000.00");
		
		softAssert.assertAll();
	}
	
	
	

}