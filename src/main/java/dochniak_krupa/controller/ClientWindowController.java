package dochniak_krupa.controller;

import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.*;
import dochniak_krupa.model.enum_type.AccountType;
import dochniak_krupa.session.SessionPreferences;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ClientWindowController implements Initializable {
  @FXML private Label accountBalanceAmountLabel;
  @FXML private Label accountBalanceCurrencyLabel;

  @FXML private TextField newTransferReceiverAccountTxtField;
  @FXML private TextField newTransferAmountOfMoneyTxtField;
  @FXML private TextField newTransferAmountOfDivisionalCurrencyTextField;
  @FXML private ChoiceBox<String> newTransferCurrencyChoiceBox;

  @FXML private DatePicker transferHistoryDateFrom;
  @FXML private DatePicker transferHistoryDateTo;
  @FXML private TextField transferHistoryReceiverAccountNumber;

  @FXML private TextArea transferHistoryLogAmount;
  @FXML private TextArea transferHistoryLogCurrencyIso;
  @FXML private TextArea transferHistoryLogReceiverAccountNumber;
  @FXML private TextArea transferHistoryLogTransactionTime;

  @FXML private TextArea listOfCardsTxtArea;

  @FXML private TextField myAccountPeselTextField;
  @FXML private ChoiceBox<AccountType> myAccountAccountTypeChoiceBox;
  @FXML private TextField myAccountFirstNameTextField;
  @FXML private TextField myAccountLastTextField;
  @FXML private DatePicker myAccountBirthDateDatePicker;
  @FXML private TextField myAccountPhoneNumberTextField;
  @FXML private TextField myAccountEmailTextField;
  @FXML private TextField myAccountLoginTextField;
  @FXML private PasswordField myAccountPasswordTextField;

  private static String myAccountNumber;

  @FXML
  private void onAccountBalanceTabClick() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      String query = "FROM AccountCurrency WHERE account_number=:accNum";
      Query sqlQuery = session.createQuery(query);
      sqlQuery.setParameter("accNum", myAccountNumber);
      List accountInfo = sqlQuery.list();
      StringBuilder amountString = new StringBuilder();
      StringBuilder currencyString = new StringBuilder();
      for (int i = 0; i < accountInfo.size(); i++) {
        amountString
            .append(
                new BigDecimal(((AccountCurrency) accountInfo.get(i)).getBalance().toString())
                    .divide(new BigDecimal("100"))
                    .setScale(2, RoundingMode.DOWN))
            .append(System.lineSeparator());
        currencyString
            .append(((AccountCurrency) accountInfo.get(i)).getCurrency().getIso())
            .append(System.lineSeparator());
      }
      accountBalanceAmountLabel.setText(amountString.toString());
      accountBalanceCurrencyLabel.setText(currencyString.toString());
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void onTransferCommit() {
    String receiverAccountNumber = newTransferReceiverAccountTxtField.getText();
    if (myAccountNumber.equals(receiverAccountNumber)) {
      showAlert("Receiver account number is equal to sender account number.");
      return;
    }

    if (!isAccountNumber(receiverAccountNumber)) {
      return;
    }

    try {
      if (new BigInteger(newTransferAmountOfMoneyTxtField.getText()).compareTo(new BigInteger("0"))
          < 0) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException nfe) {
      showAlert("Value in field amount of money is a negative number");
      return;
    }

    try {
      BigInteger bigInteger =
          new BigInteger(newTransferAmountOfDivisionalCurrencyTextField.getText());
      if (!(bigInteger.compareTo(new BigInteger("-1")) > 0)
          || !(bigInteger.compareTo(new BigInteger("100")) < 0)) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException nfe) {
      showAlert("Value in field amount divisional currency isn't a number between 0 and 99");
      return;
    }

    BigInteger value =
        new BigInteger(newTransferAmountOfMoneyTxtField.getText() + "00")
            .add(new BigInteger(newTransferAmountOfDivisionalCurrencyTextField.getText()));

    try {
      if (!(value.compareTo(new BigInteger("0")) > 0)) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException nfe) {
      showAlert("Value of money must be greater than 0.00.");
      return;
    }

    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();

      Query query =
          session.createQuery(
              "FROM AccountCurrency WHERE account_number=:number AND currency_iso=:iso");
      query.setParameter("number", myAccountNumber);
      query.setParameter("iso", newTransferCurrencyChoiceBox.getValue());
      AccountCurrency senderAC = (AccountCurrency) query.list().get(0);

      if (senderAC.getBalance().compareTo(value) < 0) {
        showAlert("You don't have enough funds!");
        return;
      }

      query.setParameter("number", receiverAccountNumber);
      query.setParameter("iso", newTransferCurrencyChoiceBox.getValue());

      if (query.list().size() == 0) {
        showAlert("Receiver's account doesn't exists or doesn't serve this currency.");
        return;
      }

      AccountCurrency receiverAC = (AccountCurrency) query.list().get(0);
      senderAC.setBalance(senderAC.getBalance().subtract(value));
      receiverAC.setBalance(receiverAC.getBalance().add(value));

      TransferLog transferLog = new TransferLog();
      transferLog.setSenderAccountNumber(myAccountNumber);
      transferLog.setReceiverAccountNumber(receiverAccountNumber);
      transferLog.setCurrencyIso(newTransferCurrencyChoiceBox.getValue());
      transferLog.setAmount(value);

      session.update(senderAC);
      session.update(receiverAC);
      session.save(transferLog);

      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
    newTransferReceiverAccountTxtField.setText("");
    newTransferAmountOfMoneyTxtField.setText("0");
    newTransferAmountOfDivisionalCurrencyTextField.setText("0");
  }

  @FXML
  private void onTransferHistorySearchClick() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      if (!isAccountNumber(transferHistoryReceiverAccountNumber.getText())) {
        return;
      }
      Query query =
          session.createQuery(
              "FROM TransferLog WHERE senderAccountNumber =: senderAccountNumber AND receiverAccountNumber =: receiverAccountNumber AND transactionTime >=: transactionTimeFrom  AND transactionTime <=: transactionTimeTo");
      query.setParameter("senderAccountNumber", "03817114205045539930363904");
      query.setParameter("receiverAccountNumber", transferHistoryReceiverAccountNumber.getText());
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        query.setParameter(
            "transactionTimeFrom", sdf.parse(transferHistoryDateFrom.getEditor().getText()));
        query.setParameter(
            "transactionTimeTo", sdf.parse(transferHistoryDateTo.getEditor().getText()));
      } catch (ParseException pe) {
        pe.printStackTrace();
      }
      List transferLogInfo = query.list();
      StringBuilder amountString = new StringBuilder();
      StringBuilder currencyIsoString = new StringBuilder();
      StringBuilder receiverAccountNumberString = new StringBuilder();
      StringBuilder transactionTimeString = new StringBuilder();

      for (int i = 0; i < transferLogInfo.size(); i++) {
        amountString
            .append(
                new BigDecimal(((TransferLog) transferLogInfo.get(i)).getAmount().toString())
                    .divide(new BigDecimal("100"))
                    .setScale(2, RoundingMode.DOWN))
            .append(System.lineSeparator());
        currencyIsoString
            .append(((TransferLog) transferLogInfo.get(i)).getCurrencyIso())
            .append(System.lineSeparator());
        receiverAccountNumberString
            .append(((TransferLog) transferLogInfo.get(i)).getReceiverAccountNumber())
            .append(System.lineSeparator());
        transactionTimeString
            .append(((TransferLog) transferLogInfo.get(i)).getTransactionTime().toString(), 0, 16)
            .append(System.lineSeparator());
      }
      transferHistoryLogAmount.setText(amountString.toString());
      transferHistoryLogCurrencyIso.setText(currencyIsoString.toString());
      transferHistoryLogReceiverAccountNumber.setText(receiverAccountNumberString.toString());
      transferHistoryLogTransactionTime.setText(transactionTimeString.toString());
    }
  }

  @FXML
  private void listAllCardsIntoTxtArea() {
    listOfCardsTxtArea.clear();
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      String query = "FROM CreditCard WHERE account_number=:accNum";
      Query sqlQuery = session.createQuery(query);
      sqlQuery.setParameter("accNum", myAccountNumber);
      List cards = sqlQuery.list();
      if (cards.iterator().hasNext()) listOfCardsTxtArea.appendText("Credit Cards:" + "\n");
      for (Object c : cards) {
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------" + "\n");
        listOfCardsTxtArea.appendText(("Number: " + ((CreditCard) c).getNumber() + "\n"));
        listOfCardsTxtArea.appendText(
            ("Verification number: " + ((CreditCard) c).getCardVerification() + "\n"));
        listOfCardsTxtArea.appendText("Expiry date: " + ((CreditCard) c).getExpiryDate() + "\n");
        listOfCardsTxtArea.appendText("Limit: " + ((CreditCard) c).getFundsLimit() + "\n");
        listOfCardsTxtArea.appendText("Used funds: " + ((CreditCard) c).getUsedFunds() + "\n");
      }

      String query2 = "FROM DebitCard WHERE account_number=:accNum";
      Query sqlQuery2 = session.createQuery(query2);
      sqlQuery2.setParameter("accNum", myAccountNumber);
      List debitCards = sqlQuery2.list();
      if (debitCards.iterator().hasNext()) {
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------"
                + System.lineSeparator()
                + "Debit Cards:"
                + System.lineSeparator());
      }
      for (Object c : debitCards) {
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------" + "\n");
        listOfCardsTxtArea.appendText(("Number: " + ((DebitCard) c).getNumber() + "\n"));
        listOfCardsTxtArea.appendText(
            ("Verification number: " + ((DebitCard) c).getCardVerification() + "\n"));
        listOfCardsTxtArea.appendText("Expiry date: " + ((DebitCard) c).getExpiryDate() + "\n");
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void onSaveInMyAccountClick() {
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(myAccountNumber);
      client.setPesel(myAccountPeselTextField.getText());
      client.setAccountType(myAccountAccountTypeChoiceBox.getValue());
      client.setFirstName(myAccountFirstNameTextField.getText());
      client.setLastName(myAccountLastTextField.getText());
      client.setBirthDate(
          new Date(
              new SimpleDateFormat("dd.MM.yyyy")
                  .parse(myAccountBirthDateDatePicker.getEditor().getText())
                  .getTime() + 43200000));
      client.setPhoneNumber(myAccountPhoneNumberTextField.getText());
      client.setEmail(myAccountEmailTextField.getText());
      client.setLogin(myAccountLoginTextField.getText());
      client.setPassword(myAccountPasswordTextField.getText());
      session.update(client);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      showAlert("Cannot execute this operation.");
    } catch (Exception ex) {
      showAlert("Cannot execute this operation.");
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    myAccountNumber = SessionPreferences.pref.get("account_number", "user");

    myAccountAccountTypeChoiceBox.getItems().add(AccountType.standard);
    myAccountAccountTypeChoiceBox.getItems().add(AccountType.savings);
    myAccountAccountTypeChoiceBox.setValue(AccountType.standard);

    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Query query = session.createQuery("FROM AccountCurrency WHERE account_number=:accNum");
      query.setParameter("accNum", myAccountNumber);
      List currencies = query.list();
      for (int i = 0; i < currencies.size(); i++) {
        newTransferCurrencyChoiceBox
            .getItems()
            .add(((AccountCurrency) currencies.get(i)).getCurrency().getIso());
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    if (newTransferCurrencyChoiceBox.getItems().size() != 0) {
      newTransferCurrencyChoiceBox.setValue(newTransferCurrencyChoiceBox.getItems().get(0));
    }

    onAccountBalanceTabClick();
  }

  private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(message);
    alert.setContentText("Please, try again");
    alert.showAndWait();
  }

  private boolean isAccountNumber(String accountNumber) {
    try {
      if (new BigInteger(accountNumber).compareTo(new BigInteger("0")) < 0) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException nfe) {
      showAlert("Value in field receiver account number isn't a proper number");
      return false;
    }
    if (accountNumber.length() != 26) {
      showAlert("Receiver account number should have 26 digits.");
      return false;
    }
    return true;
  }
}
