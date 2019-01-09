package dochniak_krupa.model;

import dochniak_krupa.model.enum_type.AccessType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(unique = true)
  @Size(max = 11)
  private String pesel;

  @Column(name = "first_name")
  @NotNull
  @Size(max = 40)
  private String firstName;

  @Column(name = "last_name")
  @NotNull
  @Size(max = 11)
  private String lastName;

  @Column(name = "birth_date")
  @NotNull private Date birthDate;

  @Column(name = "phone_number", unique = true)
  @NotNull
  @Size(max = 9)
  private String phoneNumber;

  @Column(unique = true)
  @Size(max = 60)
  private String email;

  @NotNull
  @Size(max = 40)
  private String position;

  @Enumerated(EnumType.STRING)
  @NotNull
  private AccessType access;

  @NotNull private BigInteger salary;

  @NotNull @ManyToOne private Establishment establishment;

  @Column(unique = true)
  @NotNull
  @Size(max = 30)
  private String login;

  @NotNull
  @Size(max = 30)
  private String password;

  @Column(name = "is_working")
  @NotNull
  private boolean isWorking;

  @Column(name = "log_time")
  @NotNull
  private Timestamp logTime;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    this.pesel = pesel;
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

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birth_date) {
    this.birthDate = birth_date;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public AccessType getAccess() {
    return access;
  }

  public void setAccess(AccessType access) {
    this.access = access;
  }

  public BigInteger getSalary() {
    return salary;
  }

  public void setSalary(BigInteger salary) {
    this.salary = salary;
  }

  public Establishment getEstablishment() {
    return establishment;
  }

  public void setEstablishment(Establishment establishment) {
    this.establishment = establishment;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isWorking() {
    return isWorking;
  }

  public void setWorking(boolean working) {
    isWorking = working;
  }

  public Timestamp getLogTime() {
    return logTime;
  }

  public void setLogTime(Timestamp logTime) {
    this.logTime = logTime;
  }
}
