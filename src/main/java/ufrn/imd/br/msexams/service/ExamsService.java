package ufrn.imd.br.msexams.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ufrn.imd.br.msexams.dto.EntityDTO;
import ufrn.imd.br.msexams.dto.ExamsDTO;
import ufrn.imd.br.msexams.mappers.DtoMapper;
import ufrn.imd.br.msexams.mappers.ExamsMapper;
import ufrn.imd.br.msexams.model.Exams;
import ufrn.imd.br.msexams.repository.ExamRepository;
import ufrn.imd.br.msexams.repository.GenericRepository;
import ufrn.imd.br.msexams.utils.exception.BusinessException;

import java.util.Optional;

@Service
public class ExamsService implements GenericService<Exams, ExamsDTO>{

    private final ExamRepository examsRepository;
    private final ExamsMapper examsMapper;

    public ExamsService(ExamRepository examsRepository, ExamsMapper examsMapper) {
        this.examsRepository = examsRepository;
        this.examsMapper = examsMapper;
    }

    @Override
    public GenericRepository<Exams> getRepository() {
        return this.examsRepository;
    }

    @Override
    public DtoMapper<Exams, ExamsDTO> getDtoMapper() {
        return this.examsMapper;
    }

    @Override
    public void validateBeforeSave(Exams entity){
        GenericService.super.validateBeforeSave(entity);
        validateFileId(entity.getFileId());
    }

    @Override
    public void validateBeforeUpdate(Exams entity){
        GenericService.super.validateBeforeUpdate(entity);
        validateFileId(entity.getFileId(), entity.getName());
    }



    private void validateFileId(Long fileId, String name) {
        Optional<Exams> existingProtocolWithFile = examsRepository.findByFileIdAndNameNot(fileId, name);

        if (existingProtocolWithFile.isPresent()) {
            throw new BusinessException(
                    "Arquivo inválido: " + fileId + ". Já existe outro exame cadastrado com esse arquivo.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateFileId(Long id){
        if(examsRepository.existsByFileId(id)){
            throw new BusinessException(
                    "Arquivo inválido: " + id + ". Já existe um protocolo cadastrado com esse arquivo.",
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public Page<ExamsDTO> findExamsByFilters(String name, String createdAt, String doctorId, String patientId, Pageable pageable, String examType) {
        return examsRepository.searchByFilters(name, createdAt, doctorId, patientId, pageable, examType).map(examsMapper::toDto);
    }

    public EntityDTO createExam(ExamsDTO dto) {
        Exams entity = getDtoMapper().toEntity(dto);
        validateBeforeSave(entity);
        return getDtoMapper().toDto(getRepository().save(entity));
    }
}
