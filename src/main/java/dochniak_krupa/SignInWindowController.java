package dochniak_krupa;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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
