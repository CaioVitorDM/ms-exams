package ufrn.imd.br.msexams.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ufrn.imd.br.msexams.model.Beta;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Transactional
public class CustomBetaRepositoryImpl implements CustomBetaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Beta> searchByDateRange(String patientId, String doctorId, LocalDate betaDate, Pageable pageable) {
        StringBuilder whereClause = new StringBuilder("WHERE b.active = TRUE ");

        if (betaDate != null) {
            whereClause.append("AND b.betaDate = :betaDate ");
        }

        if (patientId != null) {
            whereClause.append("AND b.patientId = :patientId ");
        }

        if (doctorId != null) {
            whereClause.append("AND b.doctorId = :doctorId ");
        }

        String countQueryStr = "SELECT COUNT(b) FROM Beta b " + whereClause.toString().trim();
        Query countQuery = entityManager.createQuery(countQueryStr);
        setQueryParameters(countQuery, betaDate, patientId, doctorId);

        long count = ((Number) countQuery.getSingleResult()).longValue();
        if (count == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, count);
        }

        String finalQuery = "SELECT b FROM Beta b " + whereClause.toString().trim() + " ORDER BY b.betaDate DESC";
        Query query = entityManager.createQuery(finalQuery, Beta.class);
        setQueryParameters(query, betaDate, patientId, doctorId);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Beta> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, count);
    }

    private void setQueryParameters(Query query, LocalDate betaDate, String patientId, String doctorId) {
        if (betaDate != null) {
            query.setParameter("betaDate", betaDate);
        }
        if (patientId != null) {
            query.setParameter("patientId", patientId);
        }
        if (doctorId != null) {
            query.setParameter("doctorId", doctorId);
        }
    }
}