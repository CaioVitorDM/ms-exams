package ufrn.imd.br.msbeta.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.br.msbeta.dto.ApiResponseDTO;
import ufrn.imd.br.msbeta.dto.BetaDTO;
import ufrn.imd.br.msbeta.model.Beta;
import ufrn.imd.br.msbeta.service.BetaService;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/beta")
//@CrossOrigin(origins = "http://localhost:4200")
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
