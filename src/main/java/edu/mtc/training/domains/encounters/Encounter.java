package edu.mtc.training.domains.encounters;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.OptBoolean;
import edu.mtc.training.constants.StringConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Encounter entity and its fields
 */
@Entity
public class Encounter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Patient id" + StringConstants.REQUIRED_FIELD)
  private Long patientId;

  private String notes;

  @NotBlank(message = "Visit code" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[a-zA-Z][0-9][a-zA-Z] [0-9][a-zA-Z][0-9]$", message = "Invalid visit code")
  private String visitCode;

  @NotBlank(message = "Provider" + StringConstants.REQUIRED_FIELD)
  private String provider;

  @NotBlank(message = "Billing code" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[0-9]{3}.[0-9]{3}.[0-9]{3}-[0-9]{2}$", message = "Invalid billing code")
  private String billingCode;

  @NotBlank(message = "Icd10" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[a-zA-Z][0-9]{2}$", message = "Invalid icd10")
  private String icd10;

  @NotBlank(message = "Total cost" + StringConstants.REQUIRED_FIELD)
  private Double totalCost;

  @NotBlank(message = "Copay" + StringConstants.REQUIRED_FIELD)
  private Double copay;

  @NotBlank(message = "Chief complaint" + StringConstants.REQUIRED_FIELD)
  private String chiefComplaint;

  private Integer pulse;
  private Integer systolic;
  private Integer diastolic;

  @NotBlank(message = "Date" + StringConstants.REQUIRED_FIELD)
  @JsonFormat(pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date date;

  public Encounter() {
  }

  public Encounter(Long patientId, String notes, String visitCode, String provider,
      String billingCode, String icd10, Double totalCost, Double copay, String chiefComplaint,
      Integer pulse, Integer systolic, Integer diastolic, Date date) {
    this.patientId = patientId;
    this.notes = notes;
    this.visitCode = visitCode;
    this.provider = provider;
    this.billingCode = billingCode;
    this.icd10 = icd10;
    this.totalCost = totalCost;
    this.copay = copay;
    this.chiefComplaint = chiefComplaint;
    this.pulse = pulse;
    this.systolic = systolic;
    this.diastolic = diastolic;
    this.date = date;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getVisitCode() {
    return visitCode;
  }

  public void setVisitCode(String visitCode) {
    this.visitCode = visitCode;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getBillingCode() {
    return billingCode;
  }

  public void setBillingCode(String billingCode) {
    this.billingCode = billingCode;
  }

  public String getIcd10() {
    return icd10;
  }

  public void setIcd10(String icd10) {
    this.icd10 = icd10;
  }

  public Double getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(Double totalCost) {
    this.totalCost = totalCost;
  }

  public Double getCopay() {
    return copay;
  }

  public void setCopay(Double copay) {
    this.copay = copay;
  }

  public String getChiefComplaint() {
    return chiefComplaint;
  }

  public void setChiefComplaint(String chiefComplaint) {
    this.chiefComplaint = chiefComplaint;
  }

  public Integer getPulse() {
    return pulse;
  }

  public void setPulse(Integer pulse) {
    this.pulse = pulse;
  }

  public Integer getSystolic() {
    return systolic;
  }

  public void setSystolic(Integer systolic) {
    this.systolic = systolic;
  }

  public Integer getDiastolic() {
    return diastolic;
  }

  public void setDiastolic(Integer diastolic) {
    this.diastolic = diastolic;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Encounter encounter)) {
      return false;
    }
    return Objects.equals(getId(), encounter.getId()) && Objects.equals(
        getPatientId(), encounter.getPatientId()) && Objects.equals(getNotes(),
        encounter.getNotes()) && Objects.equals(getVisitCode(), encounter.getVisitCode())
        && Objects.equals(getProvider(), encounter.getProvider())
        && Objects.equals(getBillingCode(), encounter.getBillingCode())
        && Objects.equals(getIcd10(), encounter.getIcd10()) && Objects.equals(
        getTotalCost(), encounter.getTotalCost()) && Objects.equals(getCopay(),
        encounter.getCopay()) && Objects.equals(getChiefComplaint(),
        encounter.getChiefComplaint()) && Objects.equals(getPulse(), encounter.getPulse())
        && Objects.equals(getSystolic(), encounter.getSystolic())
        && Objects.equals(getDiastolic(), encounter.getDiastolic())
        && Objects.equals(getDate(), encounter.getDate());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getPatientId(), getNotes(), getVisitCode(), getProvider(),
        getBillingCode(), getIcd10(), getTotalCost(), getCopay(), getChiefComplaint(), getPulse(),
        getSystolic(), getDiastolic(), getDate());
  }

  @Override
  public String toString() {
    return "Encounter{" +
        "id=" + id +
        ", patientId=" + patientId +
        ", notes='" + notes + '\'' +
        ", visitCode='" + visitCode + '\'' +
        ", provider='" + provider + '\'' +
        ", billingCode='" + billingCode + '\'' +
        ", icd10='" + icd10 + '\'' +
        ", totalCost=" + totalCost +
        ", copay=" + copay +
        ", chiefComplaint='" + chiefComplaint + '\'' +
        ", pulse=" + pulse +
        ", systolic=" + systolic +
        ", diastolic=" + diastolic +
        ", date=" + date +
        '}';
  }

  @JsonIgnore
  public Boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(patientId) &&
        Objects.isNull(notes) &&
        Objects.isNull(visitCode) &&
        Objects.isNull(provider) &&
        Objects.isNull(billingCode) &&
        Objects.isNull(icd10) &&
        Objects.equals(copay, 0.0) &&
        Objects.equals(totalCost, 0.0) &&
        Objects.isNull(chiefComplaint) &&
        Objects.equals(pulse, 0) &&
        Objects.equals(systolic, 0) &&
        Objects.equals(diastolic, 0) &&
        Objects.isNull(date);
  }
}
