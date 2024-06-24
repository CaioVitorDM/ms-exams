package ufrn.imd.br.msbeta.repository;

import ufrn.imd.br.msbeta.model.Exams;

import java.util.Optional;

public interface ExamRepository extends GenericRepository<Exams>, CustomExamRepository {

    boolean existsByFileId(Long id);
    Optional<Exams> findByFileIdAndNameNot(Long fileId, String name);



}
