package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

public class RegistrationPage {
	private ElementUtil elementUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmpassword = By.id("input-confirm");

	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input");

	private By agreeCheckBox = By.name("agree");
	private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");
	private By sucessMessg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		elementUtil = new ElementUtil(driver);
	}

	public boolean registration(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		fillForm(firstName, lastName, email, telephone, password);
		selectSubscriptionOption(subscribe);
		selectAgreementAndContinue();
		return getRegistrationStatus();

	}

	private boolean getRegistrationStatus() {
		String mesg = elementUtil.doGetText(sucessMessg);
		if (mesg.contains(Constants.REGISTER_SUCCESS_MSG)) {
			elementUtil.doClick(logoutLink);
			elementUtil.doClick(registerLink);
			return true;
		} else
			return false;
	}

	private void selectAgreementAndContinue() {

		elementUtil.doClick(agreeCheckBox);
		elementUtil.doClick(continueButton);
	}

	private void selectSubscriptionOption(String subscribe) {

		if (subscribe.equalsIgnoreCase("Yes")) {
			elementUtil.doClick(subscribeYes);
		} else {
			elementUtil.doClick(subscribeNo);

		}

	}

	private void fillForm(String firstName, String lastName, String email, String telephone, String password) {

		elementUtil.doSendKeys(this.firstName, firstName);
		elementUtil.doSendKeys(this.lastName, lastName);
		elementUtil.doSendKeys(this.email, email);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.telephone, telephone);
		elementUtil.doSendKeys(this.password, password);
		elementUtil.doSendKeys(this.confirmpassword, password);

	}
}