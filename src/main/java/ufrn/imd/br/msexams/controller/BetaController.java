package ufrn.imd.br.msexams.controller;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.br.msexams.dto.ApiResponseDTO;
import ufrn.imd.br.msexams.dto.BetaDTO;
import ufrn.imd.br.msexams.dto.EntityDTO;
import ufrn.imd.br.msexams.model.Beta;
import ufrn.imd.br.msexams.service.BetaService;

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


    @GetMapping("/find-betas")
    public ResponseEntity<ApiResponseDTO<Page<BetaDTO>>> findBetas(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) String createdAt,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false)LocalDate betaDate,
            @RequestParam(required = false)Number betaValue)
    {
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: exams retrieved successfully",
                service.findBetaByDate(patientId, doctorId, betaDate, pageable),
                null
        ));
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponseDTO<EntityDTO>> createBeta(
            @Valid @RequestBody BetaDTO dto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(
                        true,
                        "Sucess: Beta created sucessfully.",
                        service.createBeta(dto).toResponse(),
                        null
                )
        );
    }

    @PutMapping("/edit-beta/{id}")
    public ResponseEntity<ApiResponseDTO<BetaDTO>> updateBeta
            (@Valid @RequestBody BetaDTO dto){


        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDTO<>(
                        true,
                        "Sucesso: Exam edited.",
                        service.updateBeta(dto),
                        null
                )
        );
    }

}

