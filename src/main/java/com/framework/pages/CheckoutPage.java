package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

	private final By firstName = By.id("first-name");
	private final By lastName = By.id("last-name");
	private final By postalCode = By.id("postal-code");
	private final By continueBtn = By.id("continue");
	private final By finishBtn = By.id("finish");
	private final By completeHeader = By.cssSelector("[data-test='complete-header']");

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	public void enterShippingDetails(String first, String last, String postcode) {
		type(firstName, first);
		type(lastName, last);
		type(postalCode, postcode);
		click(continueBtn);
	}

	public void finishOrder() {
		click(finishBtn);
	}

	public String getCompletionMessage() {
		return text(completeHeader);
	}

	public boolean isOnCompletePage() {
		return driver.getCurrentUrl().contains("checkout-complete");
	}
}
