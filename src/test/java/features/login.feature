Feature: User Login
  As a registered user
  I want to login to nopCommerce
  So that I can access my account and make purchases

  Scenario: Successful login with valid credentials
    Given I am on the login page
    When I enter login email "validdetails@example.com"
    And I enter login password "validpassword"
    And I click the login button
    Then I should see the logout link
    And I should see the my account link

  Scenario: Login with invalid credentials should fail
    Given I am on the login page
    When I enter login email "invalid@example.com"
    And I enter login password "wrongpassword"
    And I click the login button
    Then I should see an error message about login failure

  Scenario: Successful logout
    Given I am logged in
    When I click the logout link
    Then I should be redirected to the home page
