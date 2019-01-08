package dochniak_krupa.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Entity
@Table(name = "account_currency")
public class AccountCurrency {
  @EmbeddedId AccountCurrencyId accountCurrencyId;

  @NotNull private BigInteger balance;

  @NotNull private BigInteger lendingRate;
}
