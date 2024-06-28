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
    public Page<Beta> searchByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        StringBuilder whereClause = new StringBuilder("WHERE b.active = TRUE ");

        if (startDate != null) {
            whereClause.append("AND b.betaDate >= :startDate ");
        }

        if (endDate != null) {
            whereClause.append("AND b.betaDate <= :endDate ");
        }

        String countQueryStr = "SELECT COUNT(b) FROM Beta b " + whereClause;
        Query countQuery = entityManager.createQuery(countQueryStr);
        setQueryParameters(countQuery, startDate, endDate);

        long count = ((Number) countQuery.getSingleResult()).longValue();
        if (count == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, count);
        }

        String finalQuery = "SELECT b FROM Beta b " + whereClause + "ORDER BY b.betaDate DESC";
        Query query = entityManager.createQuery(finalQuery, Beta.class);
        setQueryParameters(query, startDate, endDate);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Beta> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, count);
    }

    private void setQueryParameters(Query query, LocalDate startDate, LocalDate endDate) {
        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }
    }
}
