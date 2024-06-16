package ufrn.imd.br.msbeta.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Where;
import ufrn.imd.br.msbeta.model.builder.BetaBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "beta")
@Where(clause = "active = true")
public class Beta extends BaseEntity {

    @NotNull
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @NotNull
    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @NotNull
    @Column(name = "beta_date", nullable = false)
    private LocalDate betaDate;

    @NotNull
    @Column(name = "beta_value", nullable = false)
    private Integer betaValue;

    public static BetaBuilder builder(){
        return new BetaBuilder();
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getBetaDate() {
        return betaDate;
    }

    public void setBetaDate(LocalDate betaDate) {
        this.betaDate = betaDate;
    }

    public Integer getBetaValue() {
        return betaValue;
    }

    public void setBetaValue(Integer betaValue) {
        this.betaValue = betaValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Beta that = (Beta) o;
        return Objects.equals(patientId, that.patientId) && Objects.equals(doctorId, that.doctorId) && Objects.equals(betaDate, that.betaDate) && Objects.equals(betaValue, that.betaValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), patientId, doctorId, betaDate, betaValue);
    }


}
