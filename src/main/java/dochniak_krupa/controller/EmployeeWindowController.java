package dochniak_krupa.controller;

import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.*;
import dochniak_krupa.model.enum_type.AccountType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class EmployeeWindowController implements Initializable {
  private static Random random = new Random();

  @FXML private TextField addClientPesel = new TextField();
  @FXML private ChoiceBox<AccountType> addClientAccountType = new ChoiceBox<>();
  @FXML private TextField addClientFirstName = new TextField();
  @FXML private TextField addClientLastName = new TextField();
  @FXML private DatePicker addClientBirthDate = new DatePicker();
  @FXML private TextField addClientPhoneNumber = new TextField();
  @FXML private TextField addClientEmail = new TextField();
  @FXML private TextField addClientLogin = new TextField();
  @FXML private PasswordField addClientPassword = new PasswordField();

  @FXML private TextField deleteClientAccountNumber = new TextField();

  @FXML private TextField updateClientAccountNumber = new TextField();
  @FXML private TextField updateClientPesel = new TextField();
  @FXML private ChoiceBox<AccountType> updateClientAccountType = new ChoiceBox<>();
  @FXML private TextField updateClientFirstName = new TextField();
  @FXML private TextField updateClientLastName = new TextField();
  @FXML private DatePicker updateClientBirthDate = new DatePicker();
  @FXML private TextField updateClientPhoneNumber = new TextField();
  @FXML private TextField updateClientEmail = new TextField();
  @FXML private TextField updateClientLogin = new TextField();
  @FXML private PasswordField updateClientPassword = new PasswordField();

  @FXML private TextField searchClientAccountNumber = new TextField();
  @FXML private TextField searchClientPhoneNumber = new TextField();
  @FXML private TextField searchClientPesel = new TextField();
  @FXML private TextField searchClientEmail = new TextField();
  @FXML private TextArea searchClientOutput = new TextArea();

  @FXML private TextField accountBalanceAccountNumber = new TextField();
  @FXML private Label accountBalanceAmount = new Label();
  @FXML private Label accountBalanceCurrency = new Label();

  @FXML private DatePicker transferHistoryDateFrom = new DatePicker();
  @FXML private DatePicker transferHistoryDateTo = new DatePicker();
  @FXML private TextField transferHistorySenderAccountNumber = new TextField();
  @FXML private TextField transferHistoryReceiverAccountNumber = new TextField();
  @FXML private TextArea transferHistoryLogAmount = new TextArea();
  @FXML private TextArea transferHistoryLogCurrencyIso = new TextArea();
  @FXML private TextArea transferHistoryLogReceiverAccountNumber = new TextArea();
  @FXML private TextArea transferHistoryLogTransactionTime = new TextArea();

  @FXML private TextField createCardAccountNumber = new TextField();
  @FXML private RadioButton creditCardGenerateRadioButton = new RadioButton();
  @FXML private RadioButton debitCardGenerateRadioButton = new RadioButton();
  @FXML private TextField deleteCardCardNumber = new TextField();
  @FXML private RadioButton creditCardDeleteRadioButton = new RadioButton();
  @FXML private RadioButton debitCardDeleteRadioButton = new RadioButton();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    addClientAccountType.getItems().add(AccountType.standard);
    addClientAccountType.getItems().add(AccountType.savings);
    addClientAccountType.setValue(AccountType.standard);
    updateClientAccountType.getItems().add(AccountType.standard);
    updateClientAccountType.getItems().add(AccountType.savings);
    updateClientAccountType.setValue(AccountType.standard);
  }

  @FXML
  void createClientAccount() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(generateNumber(26));
      client.setPesel(addClientPesel.getText());
      client.setAccountType(addClientAccountType.getValue());
      client.setFirstName(addClientFirstName.getText());
      client.setLastName(addClientLastName.getText());
      client.setBirthDate(
          new Date(
              new SimpleDateFormat("dd.MM.yyyy")
                      .parse(addClientBirthDate.getEditor().getText())
                      .getTime()
                  + 43200000));
      client.setPhoneNumber(addClientPhoneNumber.getText());
      client.setEmail(addClientEmail.getText());
      client.setLogin(addClientLogin.getText());
      client.setPassword(addClientPassword.getText());
      session.save(client);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      isExecuted = false;
    } catch (Exception ex) {
      isExecuted = false;
    }
    showAlert(isExecuted);
  }

  @FXML
  void deleteClientAccount() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      Client client = session.get(Client.class, deleteClientAccountNumber.getText());
      if (!client.isActive()) {
        throw new Exception();
      }
      client.setActive(false);
      client.setLogTime(new Timestamp(System.currentTimeMillis() + 3600000));
      session.update(client);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      isExecuted = false;
    } catch (Exception ex) {
      isExecuted = false;
    }
    showAlert(isExecuted);
  }

  @FXML
  void updateClientAccount() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(updateClientAccountNumber.getText());
      client.setPesel(updateClientPesel.getText());
      client.setAccountType(updateClientAccountType.getValue());
      client.setFirstName(updateClientFirstName.getText());
      client.setLastName(updateClientLastName.getText());
      client.setBirthDate(
          new Date(
              new SimpleDateFormat("dd.MM.yyyy")
                      .parse(updateClientBirthDate.getEditor().getText())
                      .getTime()
                  + 43200000));
      client.setPhoneNumber(updateClientPhoneNumber.getText());
      client.setEmail(updateClientEmail.getText());
      client.setLogin(updateClientLogin.getText());
      client.setPassword(updateClientPassword.getText());
      session.update(client);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      isExecuted = false;
    } catch (Exception ex) {
      isExecuted = false;
    }
    showAlert(isExecuted);
  }

  @FXML
  void searchClientAccountNumber() {
    search("accountNumber", searchClientAccountNumber.getText());
  }

  @FXML
  void searchClientPhoneNumber() {
    search("phoneNumber", searchClientPhoneNumber.getText());
  }

  @FXML
  void searchClientPesel() {
    search("pesel", searchClientPesel.getText());
  }

  @FXML
  void searchClientEmail() {
    search("email", searchClientEmail.getText());
  }

  private void search(String property, String value) {
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      Criteria criteria = session.createCriteria(Client.class);
      Client client = (Client) criteria.add(Restrictions.eq(property, value)).uniqueResult();
      if (client == null) {
        searchClientOutput.setText("No that client in data base");
      } else {
        searchClientOutput.setText(
            "Account Number: "
                + client.getAccountNumber()
                + System.lineSeparator()
                + "Account Type: "
                + client.getAccountType()
                + System.lineSeparator()
                + "PESEL: "
                + client.getPesel()
                + System.lineSeparator()
                + "First Name: "
                + client.getFirstName()
                + System.lineSeparator()
                + "Last Name: "
                + client.getLastName()
                + System.lineSeparator()
                + "Birth Date: "
                + client.getBirthDate()
                + System.lineSeparator()
                + "Phone Number: "
                + client.getPhoneNumber()
                + System.lineSeparator()
                + "Email: "
                + client.getEmail()
                + System.lineSeparator()
                + "Login: "
                + client.getLogin());
      }
      tx.commit();
    } catch (HibernateException ignore) {
      if (tx != null) {
        tx.rollback();
      }
    }
  }

  @FXML
  void showAccountBalance() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Query sqlQuery = session.createQuery("FROM AccountCurrency WHERE account_number=:accNum");
      sqlQuery.setParameter("accNum", accountBalanceAccountNumber.getText());
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
      accountBalanceAmount.setText(amountString.toString());
      accountBalanceCurrency.setText(currencyString.toString());
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  @FXML
  void showTransferHistory() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      if (!(isAccountNumber(transferHistoryReceiverAccountNumber.getText())
          && isAccountNumber(transferHistorySenderAccountNumber.getText()))) {
      	showAlert(false);
        return;
      }
      Query query =
          session.createQuery(
              "FROM TransferLog WHERE senderAccountNumber =: senderAccountNumber AND receiverAccountNumber =: receiverAccountNumber AND transactionTime >=: transactionTimeFrom  AND transactionTime <=: transactionTimeTo");
      query.setParameter("senderAccountNumber", transferHistorySenderAccountNumber.getText());
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
      showAlert(true);
    }
  }

  @FXML
  void createCard() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      if (creditCardGenerateRadioButton.isSelected()) {
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(generateNumber(16));
        Client c = session.get(Client.class, createCardAccountNumber.getText());
        creditCard.setClient(c);
        creditCard.setCardVerification(generateNumber(3));
        creditCard.setExpiryDate(
            new Date(
                Calendar.getInstance().get(Calendar.YEAR) + 2,
                Calendar.getInstance().get(Calendar.MONTH) + 6,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        creditCard.setFundsLimit(new BigInteger("150000"));
        creditCard.setUsedFunds(new BigInteger("0"));
        session.save(creditCard);
      } else {
        DebitCard debitCard = new DebitCard();
        debitCard.setNumber(generateNumber(16));
        Client c = session.get(Client.class, createCardAccountNumber.getText());
        debitCard.setClient(c);
        debitCard.setCardVerification(generateNumber(3));
        debitCard.setExpiryDate(
            new Date(
                Calendar.getInstance().get(Calendar.YEAR) + 2,
                Calendar.getInstance().get(Calendar.MONTH) + 6,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        session.save(debitCard);
      }
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      isExecuted = false;
    } catch (Exception ex) {
      isExecuted = false;
    }
    showAlert(isExecuted);
  }

  @FXML
  void deleteCard() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();
      if (creditCardDeleteRadioButton.isSelected()) {
        CreditCard creditCard = session.get(CreditCard.class, deleteCardCardNumber.getText());
        session.delete(creditCard);
      } else {
        DebitCard debitCard = session.get(DebitCard.class, deleteCardCardNumber.getText());
        session.delete(debitCard);
      }
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      isExecuted = false;
    } catch (Exception ex) {
      isExecuted = false;
    }
    showAlert(isExecuted);
  }

  private static String generateNumber(int number) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < number; i++) {
      stringBuilder.append(random.nextInt(10));
    }
    return stringBuilder.toString();
  }

	private boolean isAccountNumber(String accountNumber) {
		try {
			if (new BigInteger(accountNumber).compareTo(new BigInteger("0")) < 0) {
				return false;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return accountNumber.length() == 26;
	}

  private static void showAlert(boolean isExecuted) {
    Alert alert;
    if (isExecuted) {
      alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Done!");
      alert.setContentText("Operation has been executed.");
    } else {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error!");
      alert.setContentText("Cannot execute this operation.");
    }
    alert.setHeaderText(null);
    alert.show();
  }
}
