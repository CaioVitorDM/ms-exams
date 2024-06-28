package ufrn.imd.br.msexams.service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ufrn.imd.br.msexams.dto.BetaDTO;
import ufrn.imd.br.msexams.mappers.BetaMapper;
import ufrn.imd.br.msexams.mappers.DtoMapper;
import ufrn.imd.br.msexams.model.Beta;
import ufrn.imd.br.msexams.repository.BetaRepository;
import ufrn.imd.br.msexams.repository.GenericRepository;

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
