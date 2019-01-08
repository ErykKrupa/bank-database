package dochniak_krupa.model;

import dochniak_krupa.model.enum_type.AccountType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "client")
public class Client {
  @Id
  @Column(name = "account_number", columnDefinition = "char(26)")
  @NotNull
  @Size(max = 26, min = 26)
  private String accountNumber;

  @Column(columnDefinition = "char(11)")
  @NotNull
  @Size(min = 11, max = 11)
  private String pesel;

  @Enumerated(EnumType.STRING)
  @Column(name = "account_type")
  @NotNull
  private AccountType accountType;

  @Column(name = "first_name", length = 40)
  @NotNull
  private String firstName;

  @Column(name = "last_name", length = 40)
  @NotNull
  private String lastName;

  @Column(name = "birth_date")
  @NotNull
  private Date birth_date;

  @Column(name = "phone_number", columnDefinition = "char(9)")
  @Size(min = 9, max = 9)
  private String phoneNumber;

  @Column(length = 60)
  private String email;

  @Column(length = 30)
  @NotNull
  private String login;

  @Column(length = 30)
  @NotNull
  private String password;

  @Column(name = "is_active", columnDefinition = "TINYINT(1)")
  @NotNull
  private boolean isActive;

  @Column(name = "log_time")
  @NotNull
  private Timestamp logTime;

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    this.pesel = pesel;
  }

  public AccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(AccountType accountType) {
    this.accountType = accountType;
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

  public Date getBirth_date() {
    return birth_date;
  }

  public void setBirth_date(Date birth_date) {
    this.birth_date = birth_date;
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

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public Timestamp getLogTime() {
    return logTime;
  }

  public void setLogTime(Timestamp logTime) {
    this.logTime = logTime;
  }
}
