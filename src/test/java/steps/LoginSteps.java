package steps;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.Map;

public class LoginSteps {
    private final String baseUrl = "https://swaper.com";
    private Response loginResponse;
    private Map<String, String> cookies;
    private String csrfToken;
    private String xsrfToken;
    private float openingBalance;

    @Given("User logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");
        String payload = "{ \"name\": \"testuser2@qa.com\", \"password\": \"Parole123\" }";
        loginResponse = request.body(payload)
                .post(baseUrl + "/rest/public/login");

        Assert.assertEquals(200, loginResponse.getStatusCode());

        // Извлечение куки и заголовков
        cookies = loginResponse.getCookies();
        csrfToken = loginResponse.getHeader("_csrf");
        xsrfToken = loginResponse.getHeader("x-xsrf-token");
        System.out.println("CSRF Token: " + csrfToken);
        System.out.println("XSRF Token: " + xsrfToken);
        System.out.println("Cookies: " + cookies);
    }

    @When("User fetches the account balance")
    public void userFetchesTheAccountBalance() {
        if (cookies == null || csrfToken == null ) {
            Assert.fail("User is not logged in or session information is missing.");
        }

        // Убедитесь, что все необходимые куки и заголовки передаются
        Response balanceResponse = RestAssured.given()
                .cookies(cookies)  // Использование всех полученных куки
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("_csrf", csrfToken)  // Добавление CSRF токен// Добавление XSRF токена
                .get(baseUrl + "/rest/public/profile/account-entries");

        balanceResponse.then().log().all();
        System.out.println("Fetching account balance. Status Code: " + balanceResponse.getStatusCode());

        if (balanceResponse.getStatusCode() == 200) {
            // Предполагается, что поле называется "accountBalance"
            openingBalance = balanceResponse.jsonPath().getFloat("accountBalance");
            System.out.println("Opening Balance: " + openingBalance);
        } else {
            Assert.fail("Failed to fetch account balance. Status Code: " + balanceResponse.getStatusCode());
        }
    }

    @Then("The opening balance should be 0.00")
    public void theOpeningBalanceShouldBe() {

        System.out.println("Opening Balance: " + openingBalance);
        Assert.assertEquals(0.00, openingBalance, 0.00);
    }
}
