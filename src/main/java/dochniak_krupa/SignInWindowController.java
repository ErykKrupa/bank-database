package dochniak_krupa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInWindowController implements Initializable {

    @FXML
    private TextField loginTxtField;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private ChoiceBox<String> typeOfAccountChoiceBox;

    @FXML
    private void onSignInBtnClick() {
        //todo validation
        try{
            Parent p = FXMLLoader.load(getClass().getResource("/fxml/EmployeeWindow.fxml"));
            Stage stage = new Stage();
            stage.setTitle("User banking app");
            stage.setScene(new Scene(p));
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCheckboxData();
    }

    private void loadCheckboxData(){
        typeOfAccountChoiceBox.setValue("User");
        typeOfAccountChoiceBox.getItems().addAll("User","Worker");
    }
}
