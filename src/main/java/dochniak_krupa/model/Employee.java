package dochniak_krupa.model;

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
	@Column(name = "id")
	private int id;

	@Column(name = "pesel", columnDefinition = "char(11)", unique = true)
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

	@Column(name = "phone_number", columnDefinition = "char(9)", unique = true)
	@Size(min = 9, max = 9)
	private String phoneNumber;

	@Column(name = "email", length = 60, unique = true)
	private String email;

	@Column(name = "position", length = 40)
	@NotNull
	private String position;

	@Column(name = "access")
	@Enumerated(EnumType.STRING)
	@NotNull
	private AccessType access;

	@Column(name = "salary")
	@NotNull
	private BigInteger salary;

	@Column(name = "establishment_id")
	@NotNull
	@ManyToOne
	private Establishment establishment;

	@Column(name = "login", length = 30, unique = true)
	@NotNull
	private String login;

	@Column(name = "password", length = 30)
	@NotNull
	private String password;

	@Column(name = "is_working", columnDefinition = "TINYINT(1)")
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
