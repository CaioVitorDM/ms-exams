package ufrn.imd.br.msexams.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ufrn.imd.br.msexams.model.Beta;

import java.time.LocalDate;

public interface CustomBetaRepository {
    Page<Beta> searchByDateRange(String patientId, String doctorId, LocalDate betaDate, Pageable pageable);
}
