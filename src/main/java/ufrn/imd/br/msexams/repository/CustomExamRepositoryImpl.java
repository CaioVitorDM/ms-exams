package ufrn.imd.br.msexams.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import ufrn.imd.br.msexams.model.Exams;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Transactional
public class CustomExamRepositoryImpl implements CustomExamRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String INITIAL = "SELECT e FROM Exams e WHERE e.active = TRUE ";

    @Override
    public Page<Exams> searchByFilters(String name, String createdAt, String doctorId, String patientId, Pageable pageable, String examType) {
        StringBuilder whereClause = new StringBuilder();

        String orderField = "createdAt";
        String orderDirection = "DESC";

        if (!pageable.getSort().isUnsorted()) {
            orderField = pageable.getSort().get().iterator().next().getProperty();
            orderDirection = pageable.getSort().get().iterator().next().getDirection().name();
        }

        // Construindo a cláusula WHERE
        if (name != null && !name.trim().isEmpty()) {
            whereClause.append(" AND LOWER(p.name) LIKE LOWER(:name)");
        }
        if (createdAt != null && !createdAt.trim().isEmpty()) {
            whereClause.append(" AND FUNCTION('DATE', p.createdAt) = :createdAt");
        }
        if (doctorId != null && !doctorId.trim().isEmpty()) {
            whereClause.append(" AND p.doctorId = :doctorId");
        }
        if (patientId != null && !patientId.trim().isEmpty()) {
            whereClause.append(" AND p.patientId = :patientId");
        }
        if (examType != null && !examType.trim().isEmpty()) {
            whereClause.append(" AND LOWER(p.examType) LIKE LOWER(:examType)");
        }

        String countQueryStr = "SELECT COUNT(p) FROM Exams p WHERE p.active = TRUE" + whereClause;
        Query countQuery = entityManager.createQuery(countQueryStr);
        setQueryParameters(countQuery, name, createdAt, doctorId, patientId, examType);

        long count = ((Number) countQuery.getSingleResult()).longValue();
        if (count == 0) {
            return new PageImpl<>(Collections.emptyList(), pageable, count);
        }

        String finalQuery = "SELECT p FROM Exams p WHERE p.active = TRUE" + whereClause + " ORDER BY " + orderField + " " + orderDirection;
        Query query = entityManager.createQuery(finalQuery, Exams.class);
        setQueryParameters(query, name, createdAt, doctorId, patientId, examType);

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<Exams> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, count);
    }

    private void setQueryParameters(Query query, String name, String createdAt, String doctorId, String patientId, String examType) {
        if (name != null && !name.trim().isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (createdAt != null && !createdAt.trim().isEmpty()) {
            LocalDate localDate = LocalDate.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE);
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            query.setParameter("createdAt", zonedDateTime);
        }
        if (doctorId != null && !doctorId.trim().isEmpty()) {
            query.setParameter("doctorId", doctorId);
        }
        if (patientId != null && !patientId.trim().isEmpty()) {
            query.setParameter("patientId", patientId);
        }
        if (examType != null && !examType.trim().isEmpty()) {
            query.setParameter("examType", "%" + examType + "%");
        }
    }



}
