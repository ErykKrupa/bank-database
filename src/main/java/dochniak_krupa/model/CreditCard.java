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

  @NotNull
  @ManyToOne
  @JoinColumn(name = "account_number")
  private Client client;

  @Column(name = "card_verification")
  @NotNull
  @Size(max = 3)
  private String cardVerification;

  @Column(name = "expiry_date")
  @NotNull
  private Date expiryDate;

  @Column(name = "funds_limit")
  @NotNull
  private BigInteger fundsLimit;

  @Column(name = "used_funds")
  @NotNull
  private BigInteger usedFunds;

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    if (number.length() != 16) {
      throw new IllegalArgumentException();
    }
    try {
      new BigInteger(number);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException();
    }
    this.number = number;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public String getCardVerification() {
    return cardVerification;
  }

  public void setCardVerification(String cardVerification) {
    if (cardVerification.length() != 3) {
      throw new IllegalArgumentException();
    }
    try {
      Integer.parseInt(cardVerification);
    } catch (NumberFormatException nfe) {
      throw new IllegalArgumentException();
    }
    this.cardVerification = cardVerification;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    /*if (expiryDate.compareTo(new Date(System.currentTimeMillis() + 3600)) > 0) {
      this.expiryDate = expiryDate;
    } else {
      throw new IllegalArgumentException();
    }*/
    this.expiryDate = expiryDate;
  }

  public BigInteger getFundsLimit() {
    return fundsLimit;
  }

  public void setFundsLimit(BigInteger fundsLimit) {
    if (fundsLimit.compareTo(new BigInteger("0")) > 0) {
      this.fundsLimit = fundsLimit;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public BigInteger getUsedFunds() {
    return usedFunds;
  }

  public void setUsedFunds(BigInteger usedFunds) {
    this.usedFunds = usedFunds;
  }
}
