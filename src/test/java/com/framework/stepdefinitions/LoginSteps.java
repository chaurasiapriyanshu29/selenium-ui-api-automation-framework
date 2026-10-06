package com.framework.stepdefinitions;

import com.framework.config.ConfigReader;
import com.framework.context.TestContext;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;

import org.testng.Assert;

public class LoginSteps {

	private final TestContext ctx;

	public LoginSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@Given("the user navigates to the SauceDemo application")
	public void navigate() {
		ctx.loginPage().open();
		Assert.assertTrue(ctx.loginPage().isLoginPageDisplayed(), "Login page not displayed");
	}

	@When("the user logs in with valid credentials")
	public void validLogin() {
		ctx.loginPage().login(ConfigReader.get("valid.username"), ConfigReader.get("valid.password"));
	}

	@Given("the user is logged in successfully")
	public void loggedIn() {
		navigate();
		validLogin();
		verifyLoggedIn();
	}

	@Then("the user should be logged in successfully")
	public void verifyLoggedIn() {
	}

	@Then("the login page is displayed")
	public void loginDisplayed() {
		Assert.assertTrue(ctx.loginPage().isLoginPageDisplayed());
	}

	@Then("the user validates {string} and {string}")
	public void user_validates_title_and_url(String title, String Url) {
		// Map<String, String> data = dataTable.asMap(String.class, String.class);

		Assert.assertEquals(ctx.commonMethods().getPageTitle(), title, "Page title mismatch");
		Assert.assertTrue(ctx.commonMethods().getCurrentUrl().contains(Url),
				"URL mismatch, actual: " + ctx.commonMethods().getCurrentUrl());
		Assert.assertTrue(ctx.productsPage().isDisplayed(), "Products page not displayed");
	}

}