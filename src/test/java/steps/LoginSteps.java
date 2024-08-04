package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

public class LoginSteps {
    private final String baseUrl = "https://swaper.com";
    private Response response;
    private String token;

    @Given("User logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        String payload = "{ \"username\": \"testuser@qa.com\", \"password\": \"Parole123\" }";

        response = request.body(payload)
                .post(baseUrl + "/rest/public/login");

        Assert.assertEquals(200, response.getStatusCode());
        token = response.jsonPath().getString("token");
    }

    @When("User fetches the account balance")
    public void userFetchesTheAccountBalance() {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);

        response = request.get(baseUrl + "/rest/public/profile/account-entries");

        Assert.assertEquals(200, response.getStatusCode());
    }

    @Then("The opening balance should be {double}")
    public void theOpeningBalanceShouldBe(double expectedOpeningBalance) {
        double openingBalance = response.jsonPath().getDouble("openingBalance");
        Assert.assertEquals(expectedOpeningBalance, openingBalance,0.00);
}
}