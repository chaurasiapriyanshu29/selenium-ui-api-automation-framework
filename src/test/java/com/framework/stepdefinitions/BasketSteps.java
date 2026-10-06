package com.framework.stepdefinitions;

import com.framework.config.ConfigReader;
import com.framework.context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class BasketSteps {

	private final TestContext ctx;

	public BasketSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@When("the user adds the product {string} to the basket")
	public void addDefaultProduct(String productName) throws InterruptedException {
		Thread.sleep(2000);
		ctx.productsPage().addProductToBasket(productName);
	}

	@Then("the basket count should be updated to {int}")
	public void basketCount(int expected) {
		Assert.assertEquals(ctx.productsPage().basketCount(), expected, "Basket count mismatch");
	}
}
