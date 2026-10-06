package com.framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/** UI runner - executed once per browser defined in testng.xml. */
@CucumberOptions(features = "src/test/resources/features/ui", glue = { "com.framework.stepdefinitions",
		"com.framework.hooks", "com.framework.context" }, plugin = { "pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"html:target/cucumber-reports/ui.html", "json:target/cucumber-reports/ui.json" }, monochrome = true)
public class TestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
