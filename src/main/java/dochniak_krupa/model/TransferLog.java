package dochniak_krupa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "transfer_log")
public class TransferLog {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "sender_account_number")
  @Size(max = 26)
  @NotNull
  private String senderAccountNumber;

  @Column(name = "receiver_account_number")
  @Size(max = 26)
  @NotNull
  private String receiverAccountNumber;

  @Column(name = "currency_iso")
  @Size(max = 3)
  @NotNull
  private String currencyIso;

  @NotNull private BigInteger amount;

  @Column(name = "transaction_time")
  @NotNull
  private Timestamp transactionTime = (new Timestamp(System.currentTimeMillis() + 3600000));

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSenderAccountNumber() {
    return senderAccountNumber;
  }

  public void setSenderAccountNumber(String senderAccountNumber) {
    this.senderAccountNumber = senderAccountNumber;
  }

  public String getReceiverAccountNumber() {
    return receiverAccountNumber;
  }

  public void setReceiverAccountNumber(String receiverAccountNumber) {
    this.receiverAccountNumber = receiverAccountNumber;
  }

  public String getCurrencyIso() {
    return currencyIso;
  }

  public void setCurrencyIso(String currencyIso) {
    this.currencyIso = currencyIso;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public void setAmount(BigInteger amount) {
    this.amount = amount;
  }

  public Timestamp getTransactionTime() {
    return transactionTime;
  }

  public void setTransactionTime(Timestamp transactionTime) {
    this.transactionTime = transactionTime;
  }
}
