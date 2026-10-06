package com.framework.pages;

import com.framework.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {

	protected final Logger log = LogManager.getLogger(getClass());
	protected final WebDriver driver;
	protected final WebDriverWait wait;

	protected BasePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
	}

	protected WebElement visible(By by) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	protected void click(By by) {
		log.info("Click -> {}", by);
		wait.until(ExpectedConditions.elementToBeClickable(by)).click();
	}

	protected void type(By by, String text) {
		log.info("Type -> {}", by);
		WebElement e = visible(by);
		e.clear();
		e.sendKeys(text);
	}

	protected String text(By by) {
		return visible(by).getText().trim();
	}

	protected List<WebElement> all(By by) {
		return driver.findElements(by);
	}
}
