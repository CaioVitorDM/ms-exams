package ufrn.imd.br.msexams.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
import ufrn.imd.br.msexams.utils.exception.ResourceNotFoundException;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        Optional<Exams> existingExamWithFile = examsRepository.findByFileIdAndNameNot(fileId, name);

        if (existingExamWithFile.isPresent()) {
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

    public ExamsDTO updateExam(ExamsDTO dto){
        System.out.println("Entrou em update, dto name:" + dto.id());
        Exams updatedEntity = examsMapper.toEntity(dto);
        Long examId = dto.id();

        Exams bdEntity = examsRepository.findById(examId).orElseThrow(() -> new BusinessException(
                "Error: Exam not found with id [" + examId + "]", HttpStatus.NOT_FOUND
        ));

        BeanUtils.copyProperties(updatedEntity, bdEntity, getNullPropertyNames(updatedEntity));

//        validateBeforeUpdate(bdEntity, token);

        return examsMapper.toDto(examsRepository.save(bdEntity));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

//    public EntityDTO updateExam(Long id, ExamsDTO dto) {
//        Exams existingExam = examsRepository.findById(id).orElseThrow(() ->
//                new ResourceNotFoundException("Exame não encontrado com ID: " + id)
//        );
//        System.out.println("Recebido em Service");
////        System.out.println(existingExam.getName());
////        existingExam.setExamType(dto.examType());
////        existingExam.setName(dto.name());
////        existingExam.setExamDate(dto.examDate());
////        existingExam.setFileId(dto.fileId());
////        existingExam.setDoctorId(dto.doctorId());
////        existingExam.setPatientId(dto.patientId());
//        // Atualizar outros campos conforme necessário
//        System.out.println(existingExam.getName());
//
//
//        // Validar antes de salvar
//        validateBeforeUpdate(existingExam);
//
//        Exams updatedExam = examsRepository.save(existingExam);
//
//        return examsMapper.toDto(updatedExam);
//    }
}
