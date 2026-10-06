package com.framework.stepdefinitions;

import com.framework.api.models.Post;
import com.framework.api.services.PostService;
import com.framework.context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

public class ApiSteps {

	private final TestContext ctx;
	private final PostService postService = new PostService();

	public ApiSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	private void store(String action, Response response) {
		ctx.setApiResponse(response);
		ctx.getScenario().log(action + " -> " + response.statusCode() + " | " + response.asString());
	}

	
	@When("the user retrieves the post with id {int}")
	public void get(int id) {
		store("GET /posts/" + id, postService.getPost(id));
	}

	@When("the user creates a post with title {string}, body {string} and userId {int}")
	public void create(String title, String body, int userId) {
		store("POST /posts", postService.createPost(new Post(userId, title, body)));
	}

	@When("the user updates post {int} with title {string}, body {string} and userId {int}")
	public void update(int id, String title, String body, int userId) {
		store("PUT /posts/" + id, postService.updatePost(id, new Post(userId, title, body)));
	}

	@When("the user deletes the post with id {int}")
	public void delete(int id) {
		store("DELETE /posts/" + id, postService.deletePost(id));
	}

	// ---------- Then ----------
	@Then("the response status code should be {int}")
	public void status(int expected) {
		Assert.assertEquals(ctx.getApiResponse().statusCode(), expected, "Unexpected status code");
	}

	@Then("the post should have id {int} and userId {int}")
	public void verifyIds(int id, int userId) {
		Post post = ctx.getApiResponse().as(Post.class);
		Assert.assertEquals(post.getId(), Integer.valueOf(id), "id");
		Assert.assertEquals(post.getUserId(), Integer.valueOf(userId), "userId");
	}

	@Then("the post title and body should not be empty")
	public void verifyNotEmpty() {
		Post post = ctx.getApiResponse().as(Post.class);
		Assert.assertTrue(post.getTitle() != null && !post.getTitle().isBlank(), "title is empty");
		Assert.assertTrue(post.getBody() != null && !post.getBody().isBlank(), "body is empty");
	}

	@Then("the returned post should have title {string}, body {string} and userId {int}")
	public void verifyPayload(String title, String body, int userId) {
		Post post = ctx.getApiResponse().as(Post.class);
		Assert.assertEquals(post.getTitle(), title, "title");
		Assert.assertEquals(post.getBody(), body, "body");
		Assert.assertEquals(post.getUserId(), Integer.valueOf(userId), "userId");
	}

	@Then("the returned post should have a generated id")
	public void verifyGeneratedId() {
		Assert.assertNotNull(ctx.getApiResponse().as(Post.class).getId(), "id not returned");
	}
}
