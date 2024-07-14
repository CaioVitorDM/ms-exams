package ufrn.imd.br.msexams.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record BetaDTO(long id,
                      Long patientId,
                      Long doctorId,
                      LocalDate betaDate,
                      Integer betaValue) implements EntityDTO{
    @Override
    public EntityDTO toResponse() {
        return new BetaDTO(
                this.id(),
                this.patientId(),
                this.doctorId(),
                this.betaDate(),
                this.betaValue()
        );
    }
}
