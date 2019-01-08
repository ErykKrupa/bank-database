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
  @Column(columnDefinition = "char(16)")
  @Size(min = 16, max = 16)
  private String number;

  @ManyToOne Client client;

  @Column(name = "card_verification", columnDefinition = "char(3)")
  @NotNull
  @Size(min = 3, max = 3)
  private String cardVerification;

  @Column(name = "expiry_date")
  @NotNull
  private Date expiryDate;

  @NotNull private BigInteger limit;

  @Column(name = "used_funds")
  @NotNull
  private BigInteger usedFunds;

  @Column(name = "lending_rate")
  @NotNull
  private BigInteger lendingRate;
}
