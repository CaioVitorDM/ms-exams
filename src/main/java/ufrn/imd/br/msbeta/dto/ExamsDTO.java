package ufrn.imd.br.msbeta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExamsDTO(Long id, String name, String examType, Long fileId, Long doctorId, Long patientId, LocalDate examDate, ZonedDateTime createdAt) implements  EntityDTO {
    @Override
    public EntityDTO toResponse() {
        return new ExamsDTO(
                this.id(),
                this.name(),
                this.examType(),
                this.fileId(),
                this.doctorId(),
                this.patientId(),
                this.examDate(),
                this.createdAt()
        );
    }
}
