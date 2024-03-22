package au.com.telstra.simcardactivator.api.repository;

import au.com.telstra.simcardactivator.api.service.SIMActivationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SIMActivationRecordRepository extends JpaRepository<SIMActivationRecord, Long> {
}
