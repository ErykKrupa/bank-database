package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Table(name = "account_currency")
public class AccountCurrency implements Serializable {
  @Id
  @ManyToOne
  @JoinColumn(name = "account_number")
  private Client client;

  @Id @ManyToOne private Currency currency;

  @NotNull private BigInteger balance;

  @Column(name = "lending_rate")
  @NotNull
  private BigInteger lendingRate;

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public Currency getCurrency() {
    return currency;
  }

  public BigInteger getBalance() {
    return balance;
  }

  public void setBalance(BigInteger balance) {
    this.balance = balance;
  }

  public BigInteger getLendingRate() {
    return lendingRate;
  }

  public void setLendingRate(BigInteger lendingRate) {
    this.lendingRate = lendingRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AccountCurrency that = (AccountCurrency) o;

    if (!client.equals(that.client)) return false;
    return currency.equals(that.currency);
  }

  @Override
  public int hashCode() {
    int result = client.hashCode();
    result = 31 * result + currency.hashCode();
    return result;
  }
}
