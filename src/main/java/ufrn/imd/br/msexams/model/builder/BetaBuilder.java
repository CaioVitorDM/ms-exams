package ufrn.imd.br.msexams.model.builder;

import ufrn.imd.br.msexams.model.Beta;

import java.time.LocalDate;


public class BetaBuilder {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDate betaDate;
    private Integer betaValue;

    public BetaBuilder id(Long id){
        this.id = id;
        return this;
    }

    public BetaBuilder patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public BetaBuilder doctorId(Long doctorId) {
        this.doctorId = doctorId;
        return this;
    }

    public BetaBuilder betaDate(LocalDate betaDate) {
        this.betaDate = betaDate;
        return this;
    }

    public BetaBuilder betaValue(Integer betaValue) {
        this.betaValue = betaValue;
        return this;
    }

    public Beta build(){
        Beta beta = new Beta();
        beta.setId(id);
        beta.setPatientId(patientId);
        beta.setDoctorId(doctorId);
        beta.setBetaDate(betaDate);
        beta.setBetaValue(betaValue);
        return beta;
    }
}
