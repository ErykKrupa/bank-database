package dochniak_krupa.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {

  private static SessionFactory sessionFactory;

  public static void setSessionFactory(String username, String password){
    try {
      Configuration cfg = new Configuration();
      cfg.configure("hibernate.cfg.xml");
      cfg.getProperties().setProperty("hibernate.connection.username",username);
      cfg.getProperties().setProperty("hibernate.connection.password",password);
      sessionFactory = cfg.configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public static SessionFactory getSessionFactory() {
    return sessionFactory;
  }
}
