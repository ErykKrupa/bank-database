package dochniak_krupa.fill_database;

import com.github.javafaker.Faker;
import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.*;
import dochniak_krupa.model.enum_type.AccessType;
import dochniak_krupa.model.enum_type.AccountType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class FillDatabase {
  private static Random random = new Random();
  private static Faker faker = new Faker();

  public static void main(String[] args) {
    HibernateUtility.setSessionFactory("root","root");
    fillCurrencies();
    fillClients(10000);
    fillCreditCards();
    fillDebitCards();
    fillAccountCurrencies();
    fillTransactions(10000);
    fillEmployees(10000);
  }

  private static void fillDebitCards() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Query q = session.createQuery("FROM Client");
      List clients = q.list();

      for (Object c : clients) {
        Transaction tx = session.beginTransaction();
        DebitCard dc = new DebitCard();
        dc.setNumber(generateNumber(16));
        dc.setClient(((Client) c));

        dc.setCardVerification(generateNumber(3));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(((Client) c).getLogTime().getTime()));
        calendar.add(Calendar.YEAR, 3);

        dc.setExpiryDate(new Date(calendar.getTimeInMillis()));
        session.save(dc);
        tx.commit();
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  private static void fillCreditCards() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Query q = session.createQuery("FROM Client");
      List clients = q.list();

      for (Object c : clients) {
        Transaction tx = session.beginTransaction();
        CreditCard cc = new CreditCard();
        cc.setNumber(generateNumber(16));
        cc.setClient(((Client) c));
        cc.setCardVerification(generateNumber(3));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(((Client) c).getLogTime().getTime()));
        calendar.add(Calendar.YEAR, 3);
        cc.setExpiryDate(new Date(calendar.getTimeInMillis()));

        cc.setFundsLimit(new BigInteger(String.valueOf(random.nextInt(100) * 100000 + 100000)));
        cc.setUsedFunds(
            new BigInteger(
                String.valueOf(random.nextInt(Integer.parseInt(cc.getFundsLimit().toString())))));

        session.save(cc);
        tx.commit();
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  private static void fillCurrencies() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Transaction tx = session.beginTransaction();

      Currency c = new Currency();
      c.setIso("PLN");
      c.setCurrencyName("Polish Zloty");
      c.setExchangeToDollar(new BigInteger("026"));
      session.save(c);

      Currency c2 = new Currency();
      c2.setIso("USD");
      c2.setCurrencyName("US Dollar");
      c2.setExchangeToDollar(new BigInteger("100"));
      session.save(c2);

      Currency c3 = new Currency();
      c3.setIso("GBP");
      c3.setCurrencyName("British Pound");
      c3.setExchangeToDollar(new BigInteger("128"));
      session.save(c3);

      tx.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  private static void fillAccountCurrencies() {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Query q = session.createQuery("FROM Client");
      List clients = q.list();

      for (Object c : clients) {
        Transaction tx = session.beginTransaction();
        AccountCurrency ac = new AccountCurrency();
        ac.setClient((Client) c);
        Currency curr = session.get(Currency.class, generateCurrencyIso());
        ac.setCurrency(curr);
        ac.setBalance(new BigInteger(String.valueOf(random.nextInt(100000000))));
        ac.setLendingRate(new BigInteger(String.valueOf(random.nextInt(300) + 200)));

        session.save(ac);
        tx.commit();
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  private static void fillClients(int number) {
    for (int i = 0; i < number; i++) {
      try (Session session = HibernateUtility.getSessionFactory().openSession()) {
        Transaction tx = session.beginTransaction();

        Client c = new Client();

        c.setBirthDate(new Date(faker.date().birthday(18, 75).getTime()));
        c.setPesel(generatePeselUsingBirthdate(c.getBirthDate()));
        c.setAccountNumber(generateNumber(26));
        c.setAccountType(generateAccountType());
        c.setFirstName(faker.name().firstName());
        c.setLastName(faker.name().lastName());
        c.setPhoneNumber(generateNumber(9));
        c.setEmail(
            c.getFirstName().replace("'", "")
                + c.getLastName().replace("'", "")
                + random.nextInt(100)
                + "@domain.com");
        c.setLogin(generateNumber(8));
        c.setPassword(faker.code().asin());
        c.setActive(generateIsActiveValue());
        c.setLogTime(new Timestamp(faker.date().birthday(1, 25).getTime()));

        session.save(c);
        tx.commit();
      } catch (HibernateException e) {
        e.printStackTrace();
      }
    }
  }

  private static void fillEmployees(int number) {
    for (int i = 0; i < number; i++) {
      try (Session session = HibernateUtility.getSessionFactory().openSession()) {
        Transaction tx = session.beginTransaction();

        Employee em = new Employee();

        em.setBirthDate(new Date(faker.date().birthday(18, 60).getTime()));
        em.setPesel(generatePeselUsingBirthdate(em.getBirthDate()));
        em.setFirstName(faker.name().firstName());
        em.setLastName(faker.name().lastName());
        em.setPhoneNumber(generateNumber(9));
        em.setEmail(
            em.getFirstName().replace("'", "")
                + em.getLastName().replace("'", "")
                + random.nextInt(100)
                + "@bankemployee.com");
        em.setPosition(generatePosition());
        if (em.getPosition().equals("CEO")) em.setAccess(AccessType.CEO);
        else em.setAccess(AccessType.common);
        em.setSalary(generateSalary(em.getPosition()));
        em.setLogin(generateNumber(8));
        em.setPassword(faker.code().asin());
        em.setWorking(generateIsActiveValue());
        em.setLogTime(new Timestamp(faker.date().birthday(1, 25).getTime()));

        session.save(em);

        tx.commit();
      } catch (HibernateException e) {
        e.printStackTrace();
      }
    }
  }

  private static void fillTransactions(int number) {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Query q = session.createQuery("SELECT COUNT(c) FROM Client c");
      List count = q.list();
      int numberOfClients = (int) (long) count.get(0);

      Query query = session.createQuery("SELECT accountNumber FROM Client");
      List list = query.list();

      for (int i = 0; i < number; i++) {
        Transaction tx = session.beginTransaction();
        TransferLog t = new TransferLog();
        t.setSenderAccountNumber((String) list.get(random.nextInt(numberOfClients)));
        t.setReceiverAccountNumber((String) list.get(random.nextInt(numberOfClients)));
        t.setCurrencyIso("PLN");
        t.setAmount(new BigInteger(String.valueOf(random.nextInt(50000) * 1000)));
        t.setTransactionTime(new Timestamp(faker.date().birthday(1, 25).getTime()));
        faker.date().birthday(3, 3);

        session.save(t);
        tx.commit();
      }
    } catch (HibernateException e) {
      e.printStackTrace();
    }
  }

  private static String generateNumber(int length) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < length; i++) {
      stringBuilder.append(random.nextInt(10));
    }
    return stringBuilder.toString();
  }

  private static AccountType generateAccountType() {
    int rand = random.nextInt(7);
    if (rand <= 3) return AccountType.standard;
    else if (rand <= 5) return AccountType.savings;
    else return AccountType.for_kids;
  }

  private static String generatePeselUsingBirthdate(Date d) {
    String b = d.toString();
    StringBuilder sb = new StringBuilder();
    sb.append(b.charAt(2))
        .append(b.charAt(3))
        .append(b.charAt(5))
        .append(b.charAt(6))
        .append(b.charAt(8))
        .append(b.charAt(9));

    String n = generateNumber(5);

    return sb.toString() + n;
  }

  private static String generatePosition() {
    int rand = random.nextInt(223);
    if (rand <= 100) return "Office worker";
    else if (rand <= 200) return "Customer advisor";
    else if (rand <= 220) return "Manager";
    else return "Director";
  }

  private static boolean generateIsActiveValue() {
    int rand = random.nextInt(10);
    return !(rand == 0);
  }

  private static BigInteger generateSalary(String position) {
    switch (position) {
      case "Office worker":
        {
          int salary = random.nextInt(3000) * 10000 + 230000;
          return new BigInteger(String.valueOf(salary));
        }
      case "Customer advisor":
        {
          int salary = random.nextInt(1500) * 10000 + 230000;
          return new BigInteger(String.valueOf(salary));
        }
      case "Manager":
        {
          int salary = random.nextInt(5000) * 10000 + 400000;
          return new BigInteger(String.valueOf(salary));
        }
      case "Director":
        {
          int salary = random.nextInt(10000) * 10000 + 1000000;
          return new BigInteger(String.valueOf(salary));
        }
      default:
        {
          int salary = random.nextInt(1000) * 1000 + 230000;
          return new BigInteger(String.valueOf(salary));
        }
    }
  }

  private static String generateCurrencyIso() {
    int val = random.nextInt(3);
    if (val == 0) return "PLN";
    else if (val == 1) return "GBP";
    else return "USD";
  }
}
