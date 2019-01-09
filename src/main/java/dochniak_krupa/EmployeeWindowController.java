package dochniak_krupa;

import dochniak_krupa.model.Client;
import dochniak_krupa.model.enum_type.AccountType;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.*;

import java.sql.Date;
import java.util.Random;

public class EmployeeWindowController {
  private static Random random = new Random();

  @FXML TextField addClientPeselTextField = new TextField();
  @FXML TextField addClientAccountTypeTextField = new TextField();
  @FXML TextField addClientFirstNameTextField = new TextField();
  @FXML TextField addClientLastNameTextField = new TextField();
  @FXML TextField addClientBirthDateTextField = new TextField();
  @FXML TextField addClientPhoneNumberTextField = new TextField();
  @FXML TextField addClientEmailTextField = new TextField();
  @FXML TextField addClientLoginTextField = new TextField();
  @FXML PasswordField addClientPasswordField = new PasswordField();

  @FXML TextField updateClientAccountNumberTextField = new TextField();
  @FXML TextField updateClientPeselTextField = new TextField();
  @FXML TextField updateClientAccountTypeTextField = new TextField();
  @FXML TextField updateClientFirstNameTextField = new TextField();
  @FXML TextField updateClientLastNameTextField = new TextField();
  @FXML TextField updateClientBirthDateTextField = new TextField();
  @FXML TextField updateClientPhoneNumberTextField = new TextField();
  @FXML TextField updateClientEmailTextField = new TextField();
  @FXML TextField updateClientLoginTextField = new TextField();
  @FXML PasswordField updateClientPasswordField = new PasswordField();

  @FXML
  void createClientAccount() {
    Transaction tx = null;
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(generateAccountNumber());
      client.setPesel(addClientPeselTextField.getText());
      client.setAccountType(AccountType.standard);
      client.setFirstName(addClientFirstNameTextField.getText());
      client.setLastName(addClientLastNameTextField.getText());
      client.setBirthDate(new Date(1990, 1, 1));
      client.setPhoneNumber(addClientPhoneNumberTextField.getText());
      client.setEmail(addClientEmailTextField.getText());
      client.setLogin(addClientLoginTextField.getText());
      client.setPassword(addClientPasswordField.getText());
      session.save(client);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      System.err.println("error");
    } catch (Exception ex) {
      System.err.println("error");
    }
  }

  @FXML
  void updateClientAccount() {
    Transaction tx = null;
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(updateClientAccountNumberTextField.getText());
      client.setPesel(updateClientPeselTextField.getText());
      client.setAccountType(AccountType.standard);
      client.setFirstName(updateClientFirstNameTextField.getText());
      client.setLastName(updateClientLastNameTextField.getText());
      client.setBirthDate(new Date(1990, 1, 1));
      client.setPhoneNumber(updateClientPhoneNumberTextField.getText());
      client.setEmail(updateClientEmailTextField.getText());
      client.setLogin(updateClientLoginTextField.getText());
      client.setPassword(updateClientPasswordField.getText());
      session.save(client);
      tx.commit();
    } catch (HibernateException ex) {
      if (tx != null) {
        tx.rollback();
      }
      System.err.println("error");
    } catch (Exception ex) {
      System.err.println("error");
    }
  }

  private static String generateAccountNumber() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 26; i++) {
      stringBuilder.append(random.nextInt(10));
    }
    return stringBuilder.toString();
  }
}
