package au.com.telstra.simcardactivator.api.model;

public class SIMActivationResponse {
    private boolean success;

    public SIMActivationResponse() {}

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
