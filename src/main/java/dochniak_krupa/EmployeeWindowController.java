package dochniak_krupa;

import dochniak_krupa.model.Client;
import dochniak_krupa.model.enum_type.AccountType;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.*;

import java.sql.Date;
import java.util.Random;

public class EmployeeWindowController {
  private static Random random = new Random();
  private static boolean isExecuted;

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

  @FXML
  void createClientAccount() {
    isExecuted = true;
    Transaction tx = null;
    try (Session session = SignInWindowController.sessionFactory.openSession()) {
      tx = session.beginTransaction();
      Client client = new Client();
      client.setAccountNumber(generateAccountNumber());
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
    isExecuted = true;
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
    isExecuted = true;
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

  private static String generateAccountNumber() {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < 26; i++) {
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
