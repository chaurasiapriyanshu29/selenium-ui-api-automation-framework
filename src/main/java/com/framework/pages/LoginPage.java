package com.framework.pages;

import com.framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	private final By username = By.id("user-name");
	private final By password = By.id("password");
	private final By loginBtn = By.id("login-button");

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	public LoginPage open() {
		String url = ConfigReader.get("base.url");
		log.info("Opening {}", url);
		driver.get(url);
		return this;
	}

	public void login(String user, String pass) {
		type(username, user);
		type(password, pass);
		click(loginBtn);
	}

	public boolean isLoginPageDisplayed() {
		return visible(loginBtn).isDisplayed();
	}
}
