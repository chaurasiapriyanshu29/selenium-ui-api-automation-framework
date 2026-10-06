package com.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

	private final By title = By.cssSelector("[data-test='title']");
	private final By items = By.cssSelector(".inventory_item");
	private final By cartBadge = By.cssSelector(".shopping_cart_badge");
	private final By cartLink = By.cssSelector(".shopping_cart_link");
	private final By menuBtn = By.id("react-burger-menu-btn");
	private final By logoutLink = By.id("logout_sidebar_link");

	public ProductsPage(WebDriver driver) {
		super(driver);
	}

	public boolean isDisplayed() {
		return text(title).equalsIgnoreCase("Products");
	}

	public boolean isOnInventoryUrl() {
		return driver.getCurrentUrl().contains("inventory.html");
	}

	public int productCount() {
		visible(items);
		return all(items).size();
	}

	public void addProductToBasket(String productName) {
		By add = By.id("add-to-cart-" + productName.toLowerCase().trim().replace(' ', '-'));
		log.info("Adding product '{}' to basket", productName);
		click(add);
	}

	public int basketCount() {
		return all(cartBadge).isEmpty() ? 0 : Integer.parseInt(text(cartBadge));
	}

	public void openBasket() {
		click(cartLink);
	}

	public void logout() {
		click(menuBtn);
		click(logoutLink);
	}
}
