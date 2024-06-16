package ufrn.imd.br.msbeta.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ufrn.imd.br.msbeta.dto.BetaDTO;
import ufrn.imd.br.msbeta.model.Beta;
import ufrn.imd.br.msbeta.mappers.BetaMapper;
import ufrn.imd.br.msbeta.mappers.DtoMapper;
import ufrn.imd.br.msbeta.repository.BetaRepository;
import ufrn.imd.br.msbeta.repository.GenericRepository;

import java.time.LocalDate;

@Transactional
@Service
public class BetaService implements GenericService<Beta, BetaDTO> {

    private final BetaRepository betaRepository;
    private final BetaMapper mapper;

    public BetaService(BetaRepository betaRepository, BetaMapper mapper) {
        this.betaRepository = betaRepository;
        this.mapper = mapper;
    }

    @Override
    public GenericRepository<Beta> getRepository() {
        return betaRepository;
    }

    @Override
    public DtoMapper<Beta, BetaDTO> getDtoMapper() {
        return mapper;
    }

    public Page<BetaDTO> findBetaByDate(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return betaRepository.searchByDateRange(startDate, endDate, pageable).map(mapper::toDto);
    }
}
