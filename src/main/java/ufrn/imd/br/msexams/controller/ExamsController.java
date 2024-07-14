package ufrn.imd.br.msexams.controller;

import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.imd.br.msexams.dto.ApiResponseDTO;
import ufrn.imd.br.msexams.dto.EntityDTO;
import ufrn.imd.br.msexams.dto.ExamsDTO;
import ufrn.imd.br.msexams.model.Exams;
import ufrn.imd.br.msexams.service.ExamsService;

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
                "Success: exams retrieved successfully",
                service.findExamsByFilters(name, createdAt, doctorId, patientId, pageable, examType),
                null
        ));
    }
    @PostMapping("/save")
    ////estava createExame
    public ResponseEntity<ApiResponseDTO<EntityDTO>> createExam(
            @Valid @RequestBody ExamsDTO dto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDTO<>(
                        true,
                        "Sucess: Exam created sucessfully.",
                        service.createExam(dto).toResponse(),
                        null
                )
        );
    }


    @PutMapping("/edit-exams/{id}")
    public ResponseEntity<ApiResponseDTO<ExamsDTO>> updateExam
            (@Valid @RequestBody ExamsDTO dto){
        System.out.println("Recebido em Controller");
        System.out.println("DTO: " + dto);


        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseDTO<>(
                        true,
                        "Sucesso: Exam edited.",
                        service.updateExam(dto),
                        null
                )
        );
    }

}
