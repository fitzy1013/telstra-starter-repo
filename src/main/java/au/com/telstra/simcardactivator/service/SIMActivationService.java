package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.api.model.SIMActivationRequest;
import au.com.telstra.simcardactivator.api.model.SIMActivationResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SIMActivationService {

    private final RestTemplate restTemplate;

    public SIMActivationService() {
        this.restTemplate = new RestTemplate();
    }

    public boolean activateSIM(SIMActivationRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SIMActivationRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<SIMActivationResponse> responseEntity = restTemplate.postForEntity(
                "http://localhost:8444/actuate",
                new SIMActivationRequest(request.getIccid()),
                SIMActivationResponse.class);

        return responseEntity.getBody() != null && responseEntity.getBody().isSuccess();
    }
}
