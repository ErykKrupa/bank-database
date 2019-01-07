package dochniak_krupa;

import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.Currency;
import dochniak_krupa.model.Employee;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigInteger;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SignInWindow.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            Currency curr = new Currency();
            curr.setIso("111");
            curr.setExchangeToDollar(new BigInteger("1212"));
            curr.setName("PaN");
            session.save(curr);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        launch(args);
    }

}
