package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;

@Entity
@Table(name = "debit_card")
public class DebitCard {
  @Id
  @Column(name = "number", columnDefinition = "char(16)")
  @Size(min = 16, max = 16)
  private String number;

  @Column(name = "account_number", unique = true)
  @NotNull
  @ManyToOne
  private Client client;

  @Column(name = "card_verification", columnDefinition = "char(3)")
  @NotNull
  @Size(min = 3, max = 3)
  private String cardVerification;

  @Column(name = "expiry_date")
  @NotNull
  private Date expiryDate =
      new Date(
          Calendar.getInstance().get(Calendar.YEAR) + 2,
          Calendar.getInstance().get(Calendar.MONTH) + 6,
          Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

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
    this.expiryDate = expiryDate;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
