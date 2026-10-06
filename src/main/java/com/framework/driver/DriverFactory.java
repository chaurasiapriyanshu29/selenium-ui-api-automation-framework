package com.framework.driver;

import com.framework.config.ConfigReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public final class DriverFactory {

	private static final Logger log = LogManager.getLogger(DriverFactory.class);
	private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

	private DriverFactory() {
	}

	public static WebDriver getDriver() {
		return DRIVER.get();
	}

	public static void initDriver(String browser) {
		boolean headless = ConfigReader.getBoolean("headless");
		log.info("Launching browser={} headless={}", browser, headless);
		WebDriver driver;

		switch (browser.toLowerCase().trim()) {
		case "firefox" -> {
			FirefoxOptions o = new FirefoxOptions();
			if (headless)
				o.addArguments("-headless");
			driver = new FirefoxDriver(o);
		}
		case "chrome" -> {
			ChromeOptions o = new ChromeOptions();
			if (headless)
				o.addArguments("--headless=new", "--window-size=1920,1080");
			o.addArguments("--disable-notifications", "--incognito");
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			prefs.put("profile.password_manager_leak_detection", false); // SauceDemo triggers this popup
			o.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(o);
		}
		default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicit.wait")));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(ConfigReader.getInt("page.load.timeout")));
		if (ConfigReader.getBoolean("window.maximize") && !headless)
			driver.manage().window().maximize();
		DRIVER.set(driver);
	}

	public static void quitDriver() {
		WebDriver d = DRIVER.get();
		if (d != null) {
			d.quit();
			DRIVER.remove();
			log.info("Browser closed");
		}
	}
}
