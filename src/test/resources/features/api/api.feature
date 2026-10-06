@api
Feature: Posts API (jsonplaceholder.typicode.com)

  @smoke
  Scenario: GET - retrieve a post
    When the user retrieves the post with id 1
    Then the response status code should be 200
    And the post should have id 1 and userId 1
    And the post title and body should not be empty

  @smoke
  Scenario: POST - create a new post
    When the user creates a post with title "Selenium BDD", body "created by automation" and userId 1
    Then the response status code should be 201
    And the returned post should have title "Selenium BDD", body "created by automation" and userId 1
    And the returned post should have a generated id

  @smoke
  Scenario: PUT - update an existing post
    When the user updates post 1 with title "updated title", body "updated body" and userId 1
    Then the response status code should be 200
    And the returned post should have title "updated title", body "updated body" and userId 1

  @smoke
  Scenario: DELETE - delete an existing post
    When the user deletes the post with id 1
    Then the response status code should be 200
