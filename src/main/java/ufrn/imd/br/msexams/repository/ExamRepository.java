package ufrn.imd.br.msexams.repository;


import ufrn.imd.br.msexams.model.Exams;

import java.util.Optional;

public interface ExamRepository extends GenericRepository<Exams>, CustomExamRepository {

    boolean existsByFileId(Long id);
    Optional<Exams> findByFileIdAndNameNot(Long fileId, String name);

}
