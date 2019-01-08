package dochniak_krupa.model;

import dochniak_krupa.model.enum_type.AccountType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "employee")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  @Column(columnDefinition = "char(11)")
  @NotNull
  @Size(min = 11, max = 11)
  private String pesel;

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

  @Column(length = 40)
  @NotNull
  private String position;

  @Enumerated(EnumType.STRING)
  @NotNull
  private AccessType access;

  @NotNull private BigInteger salary;

  @Column(name = "employment_date")
  @NotNull
  private Date employmentDate;

  @ManyToOne private Establishment establishment;

  @Column(length = 30)
  @NotNull
  private String login;

  @Column(length = 30)
  @NotNull
  private String password;

  @Column(name = "is_working", columnDefinition = "TINYINT(1)")
  @NotNull
  private boolean isWorking;

  @Column(name = "log_time")
  @NotNull
  private Timestamp logTime;
}
