package ufrn.imd.br.msbeta.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ufrn.imd.br.msbeta.dto.ApiResponseDTO;
import ufrn.imd.br.msbeta.dto.BetaDTO;
import ufrn.imd.br.msbeta.model.Beta;
import ufrn.imd.br.msbeta.service.BetaService;

import java.time.LocalDate;

@RestController
@RequestMapping("/betas")
public class BetaController extends GenericController<Beta, BetaDTO, BetaService> {
    /**
     * Constructs a GenericController instance with the provided service.
     *
     * @param service The service associated with the controller.
     */
    protected BetaController(BetaService service) {
    super(service);
    }

    @GetMapping ("/find-betas")
    public ResponseEntity<ApiResponseDTO<Page<BetaDTO>>> findBetas(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: betas retrieved successfully",
                service.findBetaByDate(startDate, endDate, pageable),
                null
        ));
    }
}
