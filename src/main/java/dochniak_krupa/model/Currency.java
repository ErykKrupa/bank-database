package dochniak_krupa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity
public class Currency {
  @Id
  @Size(max = 3)
  private String iso;

  @Column(name = "currency_name", unique = true)
  @NotNull
  @Size(max = 30)
  private String currencyName;

  @Column(name = "exchange_to_dollar")
  @NotNull
  private BigInteger exchangeToDollar;

  public String getIso() {
    return iso;
  }

  public void setIso(String iso) {
    this.iso = iso;
  }

  public String getCurrencyName() {
    return currencyName;
  }

  public void setCurrencyName(String name) {
    this.currencyName = name;
  }

  public BigInteger getExchangeToDollar() {
    return exchangeToDollar;
  }

  public void setExchangeToDollar(BigInteger exchangeToDollar) {
    this.exchangeToDollar = exchangeToDollar;
  }
}
