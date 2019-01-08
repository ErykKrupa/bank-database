package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name = "credit_card")
public class CreditCard {
  @Id
  @Size(max = 16)
  private String number;

  @NotNull @ManyToOne Client client;

  @Column(name = "card_verification")
  @NotNull
  @Size(max = 3)
  private String cardVerification;

  @Column(name = "expiry_date")
  @NotNull
  private Date expiryDate;

  @Column(name = "funds_limit")
  @NotNull
  private BigInteger limit;

  @Column(name = "used_funds")
  @NotNull
  private BigInteger usedFunds;

  @Column(name = "lending_rate")
  @NotNull
  private BigInteger lendingRate;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getCardVerification() {
    return cardVerification;
  }

  public void setCardVerification(String cardVerification) {
    this.cardVerification = cardVerification;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }

  public BigInteger getLimit() {
    return limit;
  }

  public void setLimit(BigInteger limit) {
    this.limit = limit;
  }

  public BigInteger getUsedFunds() {
    return usedFunds;
  }

  public void setUsedFunds(BigInteger usedFunds) {
    this.usedFunds = usedFunds;
  }

  public BigInteger getLendingRate() {
    return lendingRate;
  }

  public void setLendingRate(BigInteger lendingRate) {
    this.lendingRate = lendingRate;
  }
}
