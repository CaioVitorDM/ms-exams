package ufrn.imd.br.msexams.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ufrn.imd.br.msexams.dto.BetaDTO;
import ufrn.imd.br.msexams.dto.EntityDTO;
import ufrn.imd.br.msexams.dto.ExamsDTO;
import ufrn.imd.br.msexams.mappers.BetaMapper;
import ufrn.imd.br.msexams.mappers.DtoMapper;
import ufrn.imd.br.msexams.model.Beta;
import ufrn.imd.br.msexams.model.Exams;
import ufrn.imd.br.msexams.repository.BetaRepository;
import ufrn.imd.br.msexams.repository.GenericRepository;
import ufrn.imd.br.msexams.utils.exception.BusinessException;

import java.beans.PropertyDescriptor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
public class BetaService implements GenericService<Beta, BetaDTO> {

    private final BetaRepository betaRepository;
    private final BetaMapper betaMapper;

    public BetaService(BetaRepository betaRepository, BetaMapper betaMapper) {
        this.betaRepository = betaRepository;
        this.betaMapper = betaMapper;
    }

    @Override
    public GenericRepository<Beta> getRepository() {
        return this.betaRepository;
    }

    @Override
    public DtoMapper<Beta, BetaDTO> getDtoMapper() {
        return this.betaMapper;
    }

    @Override
    public void validateBeforeSave(Beta entity){
        GenericService.super.validateBeforeSave(entity);
    }

    @Override
    public void validateBeforeUpdate(Beta entity){
        GenericService.super.validateBeforeUpdate(entity);
    }

    //Verificar se vale a pena manter
    public Page<BetaDTO> findBetaByDate(String patientId, String doctorId, LocalDate betaDate, Pageable pageable) {
        return betaRepository.searchByDateRange(patientId, doctorId, betaDate, pageable).map(betaMapper::toDto);
    }

    public EntityDTO createBeta(BetaDTO dto) {
        Beta entity = getDtoMapper().toEntity(dto);
        validateBeforeSave(entity);
        return getDtoMapper().toDto(getRepository().save(entity));
    }

    public BetaDTO updateBeta(BetaDTO dto){
        Beta updatedEntity = betaMapper.toEntity(dto);
        Long id = dto.id();

        Beta bdEntity = betaRepository.findById(id).orElseThrow(() -> new BusinessException(
                "Error: Exam not found with id [" + id + "]", HttpStatus.NOT_FOUND
        ));

        BeanUtils.copyProperties(updatedEntity, bdEntity, getNullPropertyNames(updatedEntity));

//        validateBeforeUpdate(bdEntity, token);

        return betaMapper.toDto(betaRepository.save(bdEntity));
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
}
