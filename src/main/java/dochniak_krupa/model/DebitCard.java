package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Date;

@Entity
@Table(name = "debit_card")
public class DebitCard {
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
}
