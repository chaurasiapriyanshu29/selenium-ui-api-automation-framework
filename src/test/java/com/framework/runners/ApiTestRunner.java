package com.framework.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/** API runner - browser independent, executed once. */
@CucumberOptions(features = "src/test/resources/features/api", glue = { "com.framework.stepdefinitions",
		"com.framework.hooks", "com.framework.context" }, plugin = { "pretty",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
				"html:target/cucumber-reports/api.html", "json:target/cucumber-reports/api.json" }, monochrome = true)
public class ApiTestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
