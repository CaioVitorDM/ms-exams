package ufrn.imd.br.msbeta.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ufrn.imd.br.msbeta.model.Beta;

import java.time.LocalDate;

public interface CustomBetaRepository {
    Page<Beta> searchByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
}
