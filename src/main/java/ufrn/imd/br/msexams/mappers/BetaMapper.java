package ufrn.imd.br.msexams.mappers;

import org.springframework.stereotype.Component;
import ufrn.imd.br.msexams.dto.BetaDTO;
import ufrn.imd.br.msexams.model.Beta;

@Component
public class BetaMapper implements DtoMapper<Beta, BetaDTO>{

    @Override
    public BetaDTO toDto(Beta entity) {
        return new BetaDTO(
                entity.getId(),
                entity.getPatientId(),
                entity.getDoctorId(),
                entity.getBetaDate(),
                entity.getBetaValue()
        );
    }

    @Override
    public Beta toEntity(BetaDTO betaDTO) {
        return Beta.builder()
                .id(betaDTO.id())
                .patientId(betaDTO.patientId())
                .doctorId(betaDTO.doctorId())
                .betaDate(betaDTO.betaDate())
                .betaValue(betaDTO.betaValue())
                .build();
    }
}

