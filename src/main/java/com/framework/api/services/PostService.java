package com.framework.api.services;

import com.framework.api.client.ApiClient;
import com.framework.api.models.Post;
import io.restassured.response.Response;

public class PostService {

	private static final String POSTS = "/posts";

	public Response getPost(int id) {
		return ApiClient.get(POSTS + "/" + id);
	}

	public Response createPost(Post post) {
		return ApiClient.post(POSTS, post);
	}

	public Response updatePost(int id, Post post) {
		return ApiClient.put(POSTS + "/" + id, post);
	}

	public Response deletePost(int id) {
		return ApiClient.delete(POSTS + "/" + id);
	}
}
