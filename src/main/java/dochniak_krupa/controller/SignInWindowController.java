package dochniak_krupa.controller;

import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.session.SessionPreferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SignInWindowController implements Initializable {

  @FXML private TextField loginTxtField;
  @FXML private TextField passwordTxtField;
  @FXML private ChoiceBox<String> typeOfAccountChoiceBox;

  @FXML
  private void onSignInBtnClick() {
    try {
      String windowToDisplay = validateSignIn(typeOfAccountChoiceBox.getValue());
      saveClientAccountNumberToSession();
      displayProperWindow(windowToDisplay);
    } catch (IllegalArgumentException e) {
      loginTxtField.setText("");
      passwordTxtField.setText("");

      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Incorrect credentials");
      alert.setHeaderText("Your login or password are incorrect!");
      alert.setContentText("Please, try again!");
      alert.showAndWait();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loadCheckboxData();
    HibernateUtility.setSessionFactory("loginuser", "loginuserpassword");
  }

  private void loadCheckboxData() {
    typeOfAccountChoiceBox.setValue("Client");
    typeOfAccountChoiceBox.getItems().addAll("Client", "Employee");
  }

  // checks if credentials exist in DB and returns value of window to display
  private String validateSignIn(String user) throws IllegalArgumentException {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      String login = loginTxtField.getText();
      String password = passwordTxtField.getText();
      if (user.equals("Client")) {
        Query sqlQuery = session.createQuery("SELECT password FROM Client WHERE login=:login");
        sqlQuery.setParameter("login", login);
        List userPassword = sqlQuery.list();
        if (!userPassword.iterator().hasNext()) throw new IllegalArgumentException();
        else {
          if (BCrypt.checkpw(password, userPassword.get(0).toString())) {
            SessionPreferences.pref.put("login", login);
            logToDBAccount(login, password);
            return "Client";
          } else {
            throw new IllegalArgumentException();
          }
        }
      } else if (user.equals("Employee")) {
        String query = "SELECT password FROM Employee WHERE login=:login";
        Query sqlQuery = session.createQuery(query);
        sqlQuery.setParameter("login", login);
        List userPassword = sqlQuery.list();
        if (!userPassword.iterator().hasNext()) throw new IllegalArgumentException();
        else {
          if (BCrypt.checkpw(password, userPassword.get(0).toString())) {
            String query2 = "SELECT access FROM Employee WHERE password=:password";
            Query sqlQuery2 = session.createQuery(query2);
            sqlQuery2.setParameter("password", password);
            List accesses = sqlQuery.list();
            logToDBAccount(login, password);
            switch (accesses.get(0).toString()) {
              case "CEO":
                return "CEO";
              case "admin":
                return "Admin";
                default: return "Employee";
            }
          }
        }
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return "Error";
  }

  private void logToDBAccount(String login, String password) {
    HibernateUtility.getSessionFactory().close();
    HibernateUtility.setSessionFactory(login, password);
  }

  private void saveClientAccountNumberToSession(){
    try(Session session = HibernateUtility.getSessionFactory().openSession()){
      Query sqlQuery2 =
              session.createQuery("SELECT accountNumber FROM Client WHERE login=:login");
      sqlQuery2.setParameter("login", SessionPreferences.pref.get("login","client"));
      List accNumber = sqlQuery2.list();
      SessionPreferences.pref.put("account_number", accNumber.get(0).toString());
    }catch (HibernateException e){
      e.printStackTrace();
    }
  }

  // loads proper fxml file depending on given parameter
  private void displayProperWindow(String user) {
    try {
      Parent p = FXMLLoader.load(getClass().getResource("/fxml/" + user + "Window.fxml"));
      Stage stage = new Stage();
      stage.setTitle("Banking app");
      stage.setScene(new Scene(p));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
