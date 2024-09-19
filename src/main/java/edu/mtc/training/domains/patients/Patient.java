package edu.mtc.training.domains.patients;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.mtc.training.constants.StringConstants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

/**
 * Patient entity and its fields
 */
@Entity
public class Patient {

  @Id
  @Column(name = "patientId", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "First Name" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[a-zA-Z'-]+$", message = StringConstants.BAD_REQUEST_FIRST_NAME)
  private String firstName;

  @NotBlank(message = "Last Name" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[a-zA-Z'-]+$", message = StringConstants.BAD_REQUEST_LAST_NAME)
  private String lastName;

  @NotBlank(message = "Social Security Number" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}$", message = StringConstants.BAD_REQUEST_SSN)
  private String ssn;

  @NotBlank(message = "Email" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$", message = StringConstants.BAD_REQUEST_EMAIL)
  @Column(unique = true)
  private String email;

  @NotBlank(message = "Street Address" + StringConstants.REQUIRED_FIELD)
  private String street;

  @NotBlank(message = "City" + StringConstants.REQUIRED_FIELD)
  private String city;

  @NotBlank(message = "State" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^[a-zA-Z]{2}$", message = StringConstants.BAD_REQUEST_STATECODE)
  private String state;

  @NotBlank(message = "Zip Code" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = StringConstants.BAD_REQUEST_ZIPCODE)
  private String postal;

  @NotBlank(message = "Age" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^\\d*$", message = StringConstants.BAD_REQUEST_AGE)
  private int age;

  @NotBlank(message = "Height" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^\\d*$", message = StringConstants.BAD_REQUEST_HEIGHT)
  private int height;

  @NotBlank(message = "Weight" + StringConstants.REQUIRED_FIELD)
  @Pattern(regexp = "^\\d*$", message = StringConstants.BAD_REQUEST_WEIGHT)
  private int weight;

  @NotBlank(message = "Insurance" + StringConstants.REQUIRED_FIELD)
  private String insurance;

  @NotBlank(message = "Gender" + StringConstants.REQUIRED_FIELD)
  private String gender;

  public Patient() {
  }

  public Patient(String firstName, String lastName, String ssn, String email, String street,
      String city, String state, String postal, int age, int height, int weight, String insurance,
      String gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.ssn = ssn;
    this.email = email;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postal = postal;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.insurance = insurance;
    this.gender = gender;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostal() {
    return postal;
  }

  public void setPostal(String postal) {
    this.postal = postal;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getInsurance() {
    return insurance;
  }

  public void setInsurance(String insurance) {
    this.insurance = insurance;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Patient patient)) {
      return false;
    }
    return getAge() == patient.getAge() && getHeight() == patient.getHeight()
        && getWeight() == patient.getWeight() && Objects.equals(getId(), patient.getId())
        && Objects.equals(getFirstName(), patient.getFirstName())
        && Objects.equals(getLastName(), patient.getLastName()) && Objects.equals(
        getSsn(), patient.getSsn()) && Objects.equals(getEmail(), patient.getEmail())
        && Objects.equals(getStreet(), patient.getStreet()) && Objects.equals(
        getCity(), patient.getCity()) && Objects.equals(getState(), patient.getState())
        && Objects.equals(getPostal(), patient.getPostal()) && Objects.equals(
        getInsurance(), patient.getInsurance()) && Objects.equals(getGender(),
        patient.getGender());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getFirstName(), getLastName(), getSsn(), getEmail(), getStreet(),
        getCity(), getState(), getPostal(), getAge(), getHeight(), getWeight(), getInsurance(),
        getGender());
  }

  @Override
  public String toString() {
    return "Patient{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", ssn='" + ssn + '\'' +
        ", email='" + email + '\'' +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", postal='" + postal + '\'' +
        ", age=" + age +
        ", height=" + height +
        ", weight=" + weight +
        ", insurance='" + insurance + '\'' +
        ", gender='" + gender + '\'' +
        '}';
  }

  @JsonIgnore
  public Boolean isEmpty() {
    return Objects.isNull(id) &&
        Objects.isNull(firstName) &&
        Objects.isNull(lastName) &&
        Objects.isNull(ssn) &&
        Objects.isNull(email) &&
        Objects.isNull(street) &&
        Objects.isNull(city) &&
        Objects.isNull(state) &&
        Objects.isNull(postal) &&
        Objects.equals(age, 0) &&
        Objects.equals(height, 0) &&
        Objects.equals(weight, 0) &&
        Objects.isNull(insurance) &&
        Objects.isNull(gender);
  }
}
