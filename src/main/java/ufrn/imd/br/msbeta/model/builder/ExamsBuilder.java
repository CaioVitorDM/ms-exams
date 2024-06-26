package ufrn.imd.br.msbeta.model.builder;

import ufrn.imd.br.msbeta.model.Exams;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class ExamsBuilder {

    private Long id;
    private String name;
    private String examType;
    private Long fileId;
    private Long doctorId;
    private Long patientId;
    private LocalDate examDate;
    private ZonedDateTime createdAt;

    public ExamsBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ExamsBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ExamsBuilder examType(String examType) {
        this.examType = examType;
        return this;
    }

    public ExamsBuilder fileId(Long fileId) {
        this.fileId = fileId;
        return this;
    }

    public ExamsBuilder doctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public ExamsBuilder patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public ExamsBuilder examDate(LocalDate examDate) {
        this.examDate = examDate;
        return this;
    }

    public ExamsBuilder createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Exams build(){
        Exams exams = new Exams();
        exams.setId(id);
        exams.setName(name);
        exams.setExamType(examType);
        exams.setFileId(fileId);
        exams.setDoctorId(doctorId);
        exams.setPatientId(patientId);
        exams.setExamDate(examDate);
        exams.setCreatedAt(createdAt);
        return exams;

    }



}
