package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.api.model.SIMActivationRequest;
import au.com.telstra.simcardactivator.api.model.SIMActivationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> activationResponse;

    @Given("the system is up and running")
    public void systemIsUpAndRunning() {
        // No specific setup needed for this step
    }

    @When("I activate ICCID {string} with customer email {string}")
    public void activateICCID(String iccid, String customerEmail) {
        String activationUrl = "http://localhost:8080/activate";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        SIMActivationRequest request = new SIMActivationRequest(iccid);
        request.setCustomerEmail(customerEmail);
        HttpEntity<SIMActivationRequest> requestEntity = new HttpEntity<>(request, headers);
        activationResponse = restTemplate.exchange(activationUrl, HttpMethod.POST, requestEntity, String.class);
    }

    @Then("the activation status for ICCID {string} should be {string} when I check the status at {string}")
    public void verifyActivationStatus(String iccid, String expectedStatus, String statusUrl) {
        ResponseEntity<String> response = restTemplate.getForEntity(statusUrl, String.class);
        boolean actualValue;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            actualValue = jsonNode.get("active").asBoolean();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertTrue("Activation status is not as expected",actualValue == Boolean.parseBoolean(expectedStatus));
    }


}