package dochniak_krupa;

import dochniak_krupa.model.Client;
import dochniak_krupa.model.CreditCard;
import dochniak_krupa.model.Currency;
import dochniak_krupa.model.DebitCard;
import dochniak_krupa.model.enum_type.AccountType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.math.BigInteger;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class EmployeeWindowController {
  private static Random random = new Random();

  @FXML TextField addClientPesel = new TextField();
  @FXML TextField addClientAccountType = new TextField();
  @FXML TextField addClientFirstName = new TextField();
  @FXML TextField addClientLastName = new TextField();
  @FXML TextField addClientBirthDate = new TextField();
  @FXML TextField addClientPhoneNumber = new TextField();
  @FXML TextField addClientEmail = new TextField();
  @FXML TextField addClientLogin = new TextField();
  @FXML PasswordField addClientPassword = new PasswordField();

  @FXML TextField deleteClientAccountNumber = new TextField();

  @FXML TextField updateClientAccountNumber = new TextField();
  @FXML TextField updateClientPesel = new TextField();
  @FXML TextField updateClientAccountType = new TextField();
  @FXML TextField updateClientFirstName = new TextField();
  @FXML TextField updateClientLastName = new TextField();
  @FXML TextField updateClientBirthDate = new TextField();
  @FXML TextField updateClientPhoneNumber = new TextField();
  @FXML TextField updateClientEmail = new TextField();
  @FXML TextField updateClientLogin = new TextField();
  @FXML PasswordField updateClientPassword = new PasswordField();

  @FXML TextField searchClientAccountNumber = new TextField();
  @FXML TextField searchClientPhoneNumber = new TextField();
  @FXML TextField searchClientPesel = new TextField();
  @FXML TextField searchClientEmail = new TextField();
  @FXML TextArea searchClientOutput = new TextArea();

  @FXML TextField createCardAccountNumber = new TextField();
  @FXML RadioButton creditCardGenerateRadioButton = new RadioButton();
  @FXML RadioButton debitCardGenerateRadioButton = new RadioButton();
  @FXML TextField deleteCardCardNumber = new TextField();
  @FXML RadioButton creditCardDeleteRadioButton = new RadioButton();
  @FXML RadioButton debitCardDeleteRadioButton = new RadioButton();

  @FXML
  void createClientAccount() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(generateNumber(26));
      client.setPesel(addClientPesel.getText());
      client.setAccountType(AccountType.standard);
      client.setFirstName(addClientFirstName.getText());
      client.setLastName(addClientLastName.getText());
      client.setBirthDate(new Date(1990, 1, 1));
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
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      Client client = session.get(Client.class, deleteClientAccountNumber.getText());
      session.delete(client);
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
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(updateClientAccountNumber.getText());
      client.setPesel(updateClientPesel.getText());
      client.setAccountType(AccountType.standard);
      client.setFirstName(updateClientFirstName.getText());
      client.setLastName(updateClientLastName.getText());
      client.setBirthDate(new Date(1990, 1, 1));
      client.setPhoneNumber(updateClientPhoneNumber.getText());
      client.setEmail(updateClientEmail.getText());
      client.setLogin(updateClientLogin.getText());
      client.setPassword(updateClientPassword.getText());
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
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
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
  void createCard() {
    boolean isExecuted = true;
    Transaction tx = null;
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      if (creditCardGenerateRadioButton.isSelected()) {
        CreditCard creditCard = new CreditCard();
        creditCard.setNumber(generateNumber(16));
        creditCard.setClient(session.get(Client.class, createCardAccountNumber.getText()));
        creditCard.setCardVerification(generateNumber(3));
        creditCard.setExpiryDate(
            new Date(
                Calendar.getInstance().get(Calendar.YEAR) + 2,
                Calendar.getInstance().get(Calendar.MONTH) + 6,
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)));
        creditCard.setFundsLimit(new BigInteger("1500"));
        creditCard.setUsedFunds(new BigInteger("0"));
        // todo fix error
        Currency currency = session.get(Currency.class, "EUR");
        creditCard.setCurrency(currency);
        session.save(creditCard);
      } else {
        DebitCard debitCard = new DebitCard();
        debitCard.setNumber(generateNumber(16));
        // todo fix error
        debitCard.setClient(session.get(Client.class, createCardAccountNumber.getText()));
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
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      if (creditCardDeleteRadioButton.isSelected()) {
        // todo fix error
        CreditCard creditCard = session.get(CreditCard.class, deleteCardCardNumber.getText());
        session.delete(creditCard);
      } else {
        // todo fix error
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
