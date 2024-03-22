package au.com.telstra.simcardactivator.api.service;


import au.com.telstra.simcardactivator.api.model.SIMActivationRequest;
import au.com.telstra.simcardactivator.api.model.SIMActivationResponse;
import au.com.telstra.simcardactivator.api.repository.SIMActivationRecordRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SIMActivationService {

    private final RestTemplate restTemplate;

    private final SIMActivationRecordRepository repository;

    public SIMActivationService(SIMActivationRecordRepository repository) {
        this.repository = repository;
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

        if (responseEntity.hasBody()) {
            SIMActivationRecord record = new SIMActivationRecord(request.getIccid(), request.getCustomerEmail(), responseEntity.getBody().isSuccess());
            repository.save(record);
        }

        return responseEntity.getBody() != null && responseEntity.getBody().isSuccess();
    }

    public SIMActivationRecord getActivationRecord(Long id) {
        return repository.findById(id).orElse(null);
    }
}

