package ufrn.imd.br.msbeta.mappers;

import org.springframework.stereotype.Component;
import ufrn.imd.br.msbeta.dto.ExamsDTO;
import ufrn.imd.br.msbeta.model.Exams;

@Component
public class ExamsMapper implements  DtoMapper<Exams, ExamsDTO> {

    @Override
    public ExamsDTO toDto(Exams entity) {
        return new ExamsDTO(
                entity.getId(),
                entity.getName(),
                entity.getExamType(),
                entity.getFileId(),
                entity.getDoctorId(),
                entity.getPatientId(),
                entity.getExamDate(),
                entity.getCreatedAt()
        );
    }

    @Override
    public Exams toEntity(ExamsDTO examsDTO) {
        return Exams.builder()
                .id(examsDTO.id())
                .name(examsDTO.name())
                .examType(examsDTO.examType())
                .fileId(examsDTO.fileId())
                .doctorId(examsDTO.doctorId())
                .patientId(examsDTO.patientId())
                .examDate(examsDTO.examDate())
                .createdAt(examsDTO.createdAt())
                .build();
    }
}