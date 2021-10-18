package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {

	private WebDriver driver;
	private ElementUtil elementUtil;

	private By productHeader = By.cssSelector("div#content h1");
	private By productImages = By.cssSelector("ul.thumbnails img");
	private By quantity = By.id("input-quantity");
	private By addToCartBtn = By.id("button-cart");
	private By prodMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=1]/li");
	private By prodpriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[position()=2]/li");

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	public String getProductHeaderText() {
		return elementUtil.doGetText(productHeader);
	}

	public int getProductImagesCount() {
		return elementUtil.waitForElementsVisible(productImages, Constants.DEFAULT_TIME_OUT).size();

	}

	public Map<String, String> getProductMetaData() {
		Map<String, String> prodMap = new HashMap<String, String>();
		String productName = elementUtil.doGetText(productHeader);
		prodMap.put("productname", productName);
		getprodMetaData(prodMap);
		getprodPriceData(prodMap);
		return prodMap;

	}

	private void getprodMetaData(Map<String, String> prodMap) {

		List<WebElement> metaList = elementUtil.getElements(prodMetaData);

		for (WebElement e : metaList) {
			String metaText = e.getText();
			String metaKey = metaText.split(":")[0].trim();
			String metaValue = metaText.split(":")[1].trim();
			
			prodMap.put(metaKey, metaValue);
		}

	}

	private void getprodPriceData(Map<String,String> prodMap) {

		List<WebElement> metaPriceList = elementUtil.getElements(prodpriceData);
		String actPrice=metaPriceList.get(0).getText().trim();
		String extTaxPrice=metaPriceList.get(1).getText().trim();
		prodMap.put("price", actPrice);
		prodMap.put("ExTaxPrice", extTaxPrice.split(":")[1].trim());


	}

}
