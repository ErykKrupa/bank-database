package dochniak_krupa.fill_database;

import com.github.javafaker.Faker;
import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.Client;
import dochniak_krupa.model.Employee;
import dochniak_krupa.model.TransferLog;
import dochniak_krupa.model.enum_type.AccessType;
import dochniak_krupa.model.enum_type.AccountType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

public class FillDatabase {
  private static Random random = new Random();
  private static Faker faker = new Faker();

  public static void main(String[] args) {
    //fillClients(10000);
    //fillTransactions(10000);
    //fillEmployees(10000);

  }

  private static void fillClients(int number) {
    for (int i = 0; i < number; i++) {
      Transaction tx;
      try (Session session = HibernateUtility.getSessionFactory().openSession()) {
        tx = session.beginTransaction();

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
        c.setLogTime(new Timestamp(faker.date().birthday(0, 25).getTime()));

        session.save(c);

        tx.commit();
      } catch (HibernateException e) {
        // e.printStackTrace();
      }
    }
  }

  private static void fillEmployees(int number) {
    for (int i = 0; i < number; i++) {
      Transaction tx;
      try (Session session = HibernateUtility.getSessionFactory().openSession()) {
        tx = session.beginTransaction();

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
        em.setLogTime(new Timestamp(faker.date().birthday(0, 25).getTime()));

        session.save(em);

        tx.commit();
      } catch (HibernateException e) {
        // e.printStackTrace();
      }
    }
  }

  private static void fillTransactions(int number) {
    try (Session session = HibernateUtility.getSessionFactory().openSession()) {
      Transaction tx;
      Query q = session.createQuery("SELECT COUNT(c) FROM Client c");
      List count = q.list();
      int in = (int) (long) count.get(0);

      Query query = session.createQuery("SELECT accountNumber FROM Client");
      List list = query.list();

      for (int i = 0; i < number; i++) {
        tx = session.beginTransaction();
        TransferLog t = new TransferLog();
        t.setSenderAccountNumber((String) list.get(random.nextInt(in)));
        t.setReceiverAccountNumber((String) list.get(random.nextInt(in)));
        t.setCurrencyIso("PLN");
        t.setAmount(new BigInteger(String.valueOf(random.nextInt(500) * 10)));
        t.setTransactionTime(new Timestamp(faker.date().birthday(0, 25).getTime()));

        session.save(t);
        tx.commit();
      }
    } catch (HibernateException e) {
      // e.printStackTrace();
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
          int value = random.nextInt(30) * 100 + 2300;
          return new BigInteger(String.valueOf(value));
        }
      case "Customer advisor":
        {
          int value = random.nextInt(15) * 100 + 2300;
          return new BigInteger(String.valueOf(value));
        }
      case "Manager":
        {
          int value = random.nextInt(50) * 100 + 4000;
          return new BigInteger(String.valueOf(value));
        }
      case "Director":
        {
          int value = random.nextInt(100) * 100 + 10000;
          return new BigInteger(String.valueOf(value));
        }
      default:
        {
          int value = random.nextInt(10) * 10 + 2300;
          return new BigInteger(String.valueOf(value));
        }
    }
  }
}
