package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

	private final By title = By.cssSelector("[data-test='title']");
	private final By items = By.cssSelector(".cart_item");
	private final By itemName = By.cssSelector(".inventory_item_name");
	private final By checkout = By.id("checkout");

	public CartPage(WebDriver driver) {
		super(driver);
	}

	public boolean isDisplayed() {
		return text(title).equalsIgnoreCase("Your Cart");
	}

	public int itemCount() {
		return all(items).size();
	}

	public boolean containsProduct(String name) {
		return all(itemName).stream().anyMatch(e -> e.getText().trim().equals(name));
	}

	public void proceedToCheckout() {
		click(checkout);
	}
}
