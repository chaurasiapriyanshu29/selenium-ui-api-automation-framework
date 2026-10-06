package com.framework.hooks;

import com.framework.config.ConfigReader;
import com.framework.context.TestContext;
import com.framework.driver.DriverFactory;
import com.framework.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.Reporter;

public class Hooks {

	private static final Logger log = LogManager.getLogger(Hooks.class);
	private final TestContext context;

	public Hooks(TestContext context) {
		this.context = context;
	}

	/** Browser priority: testng.xml parameter > -Dbrowser > config.properties */
	private String resolveBrowser() {
		ITestResult result = Reporter.getCurrentTestResult();
		if (result != null) {
			String p = result.getTestContext().getCurrentXmlTest().getParameter("browser");
			if (p != null && !p.isBlank())
				return p;
		}
		return ConfigReader.get("browser");
	}

	@Before(value = "not @api", order = 0)
	public void setUp(Scenario scenario) {
		context.setScenario(scenario);
		String browser = resolveBrowser();
		log.info("=== START: {} [{}] ===", scenario.getName(), browser);
		DriverFactory.initDriver(browser);
		scenario.log("Browser: " + browser);
	}

	@After(value = "not @api", order = 0)
	public void tearDown(Scenario scenario) {
		try {
			if (DriverFactory.getDriver() != null && scenario.isFailed()) {
				log.error("Scenario FAILED: {}", scenario.getName());
				scenario.attach(ScreenshotUtil.capture(DriverFactory.getDriver()), "image/png", "Failure screenshot");
			}
		} finally {
			DriverFactory.quitDriver();
			log.info("=== END: {} -> {} ===", scenario.getName(), scenario.getStatus());
		}
	}

	@Before(value = "@api", order = 0)
	public void apiStart(Scenario scenario) {
		context.setScenario(scenario);
		log.info("=== START API: {} ===", scenario.getName());
	}

	@After(value = "@api", order = 0)
	public void apiEnd(Scenario scenario) {
		log.info("=== END API: {} -> {} ===", scenario.getName(), scenario.getStatus());
	}
}
