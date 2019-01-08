package dochniak_krupa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity
@Table(name = "currency")
public class Currency {
  @Id
  @Column(name = "iso", columnDefinition = "char(3)")
  @Size(min = 3, max = 3)
  private String iso;

  @Column(name = "currency_name", unique = true)
  @NotNull
  @Size(max = 30)
  private String currencyName;

  @Column(name = "exchange_to_dollar")
  @NotNull
  private BigInteger exchangeToDollar;

  public Currency(
      @Size(min = 3, max = 3) String iso,
      @NotNull @Size(max = 13) String name,
      BigInteger exchangeToDollar) {
    this.iso = iso;
    this.currencyName = name;
    this.exchangeToDollar = exchangeToDollar;
  }

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public String getCurrencyNameame() {
    return currencyName;
  }

  public void setCurrencyNameame(String name) {
    this.currencyName = name;
  }

  public BigInteger getExchangeToDollar() {
    return exchangeToDollar;
  }

  public void setExchangeToDollar(BigInteger exchangeToDollar) {
    this.exchangeToDollar = exchangeToDollar;
  }
}
