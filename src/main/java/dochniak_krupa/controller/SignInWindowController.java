package dochniak_krupa.controller;

import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.Client;
import dochniak_krupa.model.Employee;
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
  }

  private void loadCheckboxData() {
    typeOfAccountChoiceBox.setValue("Client");
    typeOfAccountChoiceBox.getItems().addAll("Client", "Employee");
  }

  // checks if credentials exist in DB and returns value of window to display
  private String validateSignIn(String user) throws IllegalArgumentException {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      String query = "FROM " + user + " WHERE login=:login AND password=:password";
      Query sqlQuery = session.createQuery(query);
      sqlQuery.setParameter("login", loginTxtField.getText());
      sqlQuery.setParameter("password", passwordTxtField.getText());
      List users = sqlQuery.list();

      // if user is an employee we must check his access type
      // in order to display proper window
      if (!users.iterator().hasNext()) throw new IllegalArgumentException();
      if (user.equals("Employee")) {
        switch (((Employee) users.get(0)).getAccess().toString()) {
          case "common":
            return "Employee";
          case "CEO":
            return "CEO";
          case "admin":
            return "Admin";
        }
      } else {
        SessionPreferences.pref.put("account_number", ((Client) users.get(0)).getAccountNumber());
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    return "Client";
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
