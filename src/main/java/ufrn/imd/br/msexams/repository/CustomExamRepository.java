package ufrn.imd.br.msexams.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ufrn.imd.br.msexams.model.Exams;

public interface CustomExamRepository {

    Page<Exams> searchByFilters(String name, String createdAt, String doctorId, String patientId, Pageable pageable, String examType);


}
