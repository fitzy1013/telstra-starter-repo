package au.com.telstra.simcardactivator.api.service;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SIMActivationRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String iccid;
    private String customerEmail;
    private Boolean active;

    protected SIMActivationRecord() {}

    public SIMActivationRecord(String iccid, String customerEmail, Boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    @Override
    public String toString() {
        return String.format(
                "Service[id=%d, iccid='%s', customerEmail='%s', active='%b']",
                id, iccid, customerEmail, active);
    }

    public Long getId() {
        return id;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Boolean getActive() { return active;
    }
}
