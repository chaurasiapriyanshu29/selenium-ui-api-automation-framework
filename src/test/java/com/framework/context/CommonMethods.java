package com.framework.context;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.framework.pages.BasePage;

public class CommonMethods extends BasePage {

	public CommonMethods(WebDriver driver) {
		super(driver);
	}

	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

}
