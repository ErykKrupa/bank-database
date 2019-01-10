package dochniak_krupa.fill_database;

import com.github.javafaker.Faker;
import dochniak_krupa.database.HibernateUtility;
import dochniak_krupa.model.Client;
import dochniak_krupa.model.enum_type.AccountType;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Random;

public class FillDatabase {
  private static Random random = new Random();
  private static Faker faker = new Faker();

  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      fillEmployees(10000);
    }
  }

  private static void fillEmployees(int number) {
    for (int i = 0; i < number; i++) {
      Transaction tx = null;
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
        c.setEmail(c.getFirstName() + c.getLastName() + random.nextInt(100) + "@domain.com");
        c.setLogin(generateNumber(8));
        c.setPassword(faker.code().asin());
        c.setActive(true);
        c.setLogTime(new Timestamp(System.currentTimeMillis()));

        session.save(c);

        tx.commit();
      } catch (HibernateException e) {
        //e.printStackTrace();
      }
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
    int rand = random.nextInt(6);
    if (rand <= 3) return AccountType.standard;
    else if (rand <= 5) return AccountType.standard;
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

    return sb.toString()+n;
  }
}
