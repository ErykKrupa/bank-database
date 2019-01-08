package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "establishment")
public class Establishment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @NotNull private int directorId;

  @NotNull
  @Column(length = 60)
  private String adress;

  @NotNull // length 5?
  @Column(length = 10, name = "postal_code")
  private String postalCode;

  @NotNull
  @Column(length = 40, name = "city")
  private String city;

  @NotNull
  @Column(length = 2, name = "country")
  @Size(min = 2, max = 2)
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

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
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
