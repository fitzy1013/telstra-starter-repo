package au.com.telstra.simcardactivator.api.controller;
import au.com.telstra.simcardactivator.api.model.SIMActivationRequest;
import au.com.telstra.simcardactivator.service.SIMActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class SIMActivationController {

    private final SIMActivationService activationService;

    @Autowired
    public SIMActivationController(SIMActivationService activationService) {
        this.activationService = activationService;
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateSIM(@RequestBody SIMActivationRequest request) {
        try {
            boolean success = activationService.activateSIM(request);
            if (success) {
                return ResponseEntity.ok("SIM card activation successful.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("SIM card activation failed.");
            }
        } catch (HttpServerErrorException e) {
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}