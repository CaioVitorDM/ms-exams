package ufrn.imd.br.msbeta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import ufrn.imd.br.msbeta.model.builder.ExamsBuilder;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "exams")
@Where(clause = "active = true")
public class Exams extends BaseEntity{

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    private String examType;

    @NotNull
    @Column(name = "file_id", nullable = false, unique = true)
    private Long fileId;

    @NotNull
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @NotNull
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @NotNull
    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;


    public static ExamsBuilder builder(){
        return new ExamsBuilder();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Exams exam = (Exams) o;
        return Objects.equals(name, exam.name) && Objects.equals(examType, exam.examType) && Objects.equals(fileId, exam.fileId) && Objects.equals(patientId, exam.patientId) && Objects.equals(examDate, exam.examDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, examDate, fileId, patientId, examType);
    }
}
