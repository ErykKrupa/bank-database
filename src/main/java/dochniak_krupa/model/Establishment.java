package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Establishment {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "director_id", unique = true)
  @NotNull
  private int directorId;

  @NotNull
  @Size(max = 60)
  private String address;

  @Column(name = "postal_code")
  @NotNull
  @Size(max = 10)
  private String postalCode;

  @NotNull
  @Size(max = 40)
  private String city;

  @NotNull
  @Size(max = 2)
  private String country;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDirectorId() {
    return directorId;
  }

  public void setDirectorId(int directorId) {
    this.directorId = directorId;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String adress) {
    this.address = adress;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
