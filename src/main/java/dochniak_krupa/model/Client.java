package dochniak_krupa.model;

import dochniak_krupa.model.enum_type.AccountType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "client")
public class Client {
  @Id
  @Column(name = "account_number", columnDefinition = "char(26)")
  @Size(max = 26, min = 26)
  private String accountNumber;

  @Column(name = "pesel", columnDefinition = "char(11)", unique = true)
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
  private Date birthDate;

  @Column(name = "phone_number", columnDefinition = "char(9)", unique = true)
  @NotNull
  @Size(min = 9, max = 9)
  private String phoneNumber;

  @Column(length = 60, unique = true)
  private String email;

  @Column(length = 30, unique = true)
  @NotNull
  private String login;

  @Column(length = 30)
  @NotNull
  private String password;

  @Column(name = "is_active", columnDefinition = "TINYINT(1)")
  @NotNull
  private boolean isActive = true;

  @Column(name = "log_time")
  @NotNull
  private Timestamp logTime = new Timestamp(System.currentTimeMillis() + 3600000);

  public String getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(String accountNumber) {
    if (accountNumber.length() != 26) {
      throw new IllegalArgumentException();
    }
    try {
      new BigInteger(accountNumber);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException();
    }
    this.accountNumber = accountNumber;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    if (pesel.length() == 0) {
      this.pesel = null;
    } else {
      if (pesel.length() != 11) {
        throw new IllegalArgumentException();
      }
      try {
        new BigInteger(pesel);
      } catch (NumberFormatException nfe) {
        throw new IllegalArgumentException();
      }
      this.pesel = pesel;
    }
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
    if (firstName.length() == 0) {
      throw new IllegalArgumentException();
    }
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    if (lastName.length() == 0) {
      throw new IllegalArgumentException();
    }
    this.lastName = lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    if (phoneNumber.length() != 9) {
      throw new IllegalArgumentException();
    }
    try {
      new BigInteger(phoneNumber);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException();
    }
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    if (email.length() == 0) {
      this.email = null;
    } else {
      this.email = email;
    }
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    if (login.length() < 8) {
      throw new IllegalArgumentException();
    }
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (password.length() < 8) {
      throw new IllegalArgumentException();
    }
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
