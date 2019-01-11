package dochniak_krupa.controller;

import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.*;
import dochniak_krupa.session.SessionPreferences;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigInteger;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientWindowController implements Initializable {
  @FXML private Label amountLabel;
  @FXML private Label currencyLabel;
  @FXML private TextField receiverAccountTxtField;
  @FXML private TextField amountOfMoneyTxtField;
  @FXML private ChoiceBox<String> currencyChoiceBox;
  @FXML private TextArea listOfCardsTxtArea;

  @FXML
  private void onAccountBalanceTabClick() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      String query = "FROM AccountCurrency WHERE account_number=:accNum";
      Query sqlQuery = session.createQuery(query);
      sqlQuery.setParameter("accNum", SessionPreferences.pref.get("account_number", "client"));
      List accountInfo = sqlQuery.list();
      amountLabel.setText(((AccountCurrency) accountInfo.get(0)).getBalance().toString());
      currencyLabel.setText(((AccountCurrency) accountInfo.get(0)).getCurrency().getIso());
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void onTransferCommit() {
    Transaction tx = null;
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      tx = session.beginTransaction();

      TransferLog tlog = new TransferLog();
      tlog.setSenderAccountNumber(SessionPreferences.pref.get("account_number", "user"));
      tlog.setReceiverAccountNumber(receiverAccountTxtField.getText());
      tlog.setCurrencyIso(currencyChoiceBox.getValue());
      tlog.setAmount(new BigInteger(amountOfMoneyTxtField.getText()));
      session.save(tlog);

      if (tlog.getSenderAccountNumber().equals(tlog.getReceiverAccountNumber())) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid receiver account number!");
        alert.setHeaderText("You've chosen your own account number!");
        alert.setContentText("Please, try again!");
        alert.showAndWait();
        return;
      }

      String query = "FROM AccountCurrency WHERE account_number=:number";
      Query sqlQuery = session.createQuery(query);
      sqlQuery.setParameter("number", tlog.getSenderAccountNumber());
      List sender = sqlQuery.list();
      AccountCurrency senderAC = (AccountCurrency) sender.get(0);
      senderAC.setBalance(senderAC.getBalance().subtract(tlog.getAmount()));
      session.update(senderAC);

      if (senderAC.getBalance().subtract(tlog.getAmount()).compareTo(new BigInteger("0")) < 0) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Not enough funds!");
        alert.setHeaderText("You don't have enough funds!");
        alert.setContentText("Please, try again!");
        alert.showAndWait();
        return;
      }

      Query sqlQuery2 = session.createQuery(query);
      sqlQuery2.setParameter("number", tlog.getReceiverAccountNumber());
      List receiver = sqlQuery2.list();
      AccountCurrency receiverAC = (AccountCurrency) receiver.get(0);
      receiverAC.setBalance(receiverAC.getBalance().add(tlog.getAmount()));
      session.update(receiverAC);

      tx.commit();
      receiverAccountTxtField.setText("");
      amountOfMoneyTxtField.setText("");
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      e.printStackTrace();
    }
  }

  private void listAllCardsIntoTxtArea() {
    listOfCardsTxtArea.clear();
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      String query = "FROM CreditCard WHERE account_number=:accNum";
      Query sqlQuery = session.createQuery(query);
      sqlQuery.setParameter("accNum", SessionPreferences.pref.get("account_number", "client"));
      List cards = sqlQuery.list();
      if (cards.iterator().hasNext()) listOfCardsTxtArea.appendText("Credit Cards:" + "\n");
      for (Object c : cards) {
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------" + "\n");
        listOfCardsTxtArea.appendText(("Number: " + ((CreditCard) c).getNumber() + "\n"));
        listOfCardsTxtArea.appendText(
            ("Verification number: " + ((CreditCard) c).getCardVerification() + "\n"));
        listOfCardsTxtArea.appendText("Expiry date: " + ((CreditCard) c).getExpiryDate() + "\n");
        listOfCardsTxtArea.appendText("Limit: " + ((CreditCard) c).getLimit() + "\n");
        listOfCardsTxtArea.appendText("Used funds: " + ((CreditCard) c).getUsedFunds() + "\n");
        listOfCardsTxtArea.appendText("Lending rate: " + ((CreditCard) c).getLendingRate() + "\n");
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------" + "\n");
      }

      String query2 = "FROM DebitCard WHERE account_number=:accNum";
      Query sqlQuery2 = session.createQuery(query2);
      sqlQuery2.setParameter("accNum", SessionPreferences.pref.get("account_number", "client"));
      List debitCards = sqlQuery2.list();
      if (debitCards.iterator().hasNext()) listOfCardsTxtArea.appendText("Debit Cards:" + "\n");
      for (Object c : debitCards) {
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------" + "\n");
        listOfCardsTxtArea.appendText(("Number: " + ((DebitCard) c).getNumber() + "\n"));
        listOfCardsTxtArea.appendText(
            ("Verification number: " + ((DebitCard) c).getCardVerification() + "\n"));
        listOfCardsTxtArea.appendText("Expiry date: " + ((DebitCard) c).getExpiryDate() + "\n");
        listOfCardsTxtArea.appendText(
            "------------------------------------------------------" + "\n");
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loadChoiceBoxData();
    listAllCardsIntoTxtArea();
  }

  private void loadChoiceBoxData() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      List currencies = session.createQuery("SELECT iso FROM Currency").list();
      for (Object c : currencies) currencyChoiceBox.getItems().add(((String) c));
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    currencyChoiceBox.setValue("PLN");
  }
}
