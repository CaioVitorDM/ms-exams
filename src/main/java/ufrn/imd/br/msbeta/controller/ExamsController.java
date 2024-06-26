package ufrn.imd.br.msbeta.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.br.msbeta.dto.ApiResponseDTO;
import ufrn.imd.br.msbeta.dto.ExamsDTO;
import ufrn.imd.br.msbeta.model.Exams;
import ufrn.imd.br.msbeta.service.ExamsService;


@RestController
@RequestMapping("/v1/exams")
public class ExamsController extends GenericController<Exams, ExamsDTO, ExamsService> {
    protected ExamsController(ExamsService service) {
        super(service);
    }

    @GetMapping("/find-exams")
    public ResponseEntity<ApiResponseDTO<Page<ExamsDTO>>> findExams(
            @ParameterObject Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String createdAt,
            @RequestParam(required = false) String patientId,
            @RequestParam(required = false) String doctorId,
            @RequestParam(required = false)String examType)

    {
        return ResponseEntity.ok(new ApiResponseDTO<>(
                true,
                "Success: protocols retrieved successfully",
                service.findExamsByFilters(name, createdAt, doctorId, patientId, pageable, examType),
                null
        ));
    }

}
