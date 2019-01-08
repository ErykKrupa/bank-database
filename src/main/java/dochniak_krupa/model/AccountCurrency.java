package dochniak_krupa.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Entity
@Table(name = "account_currency")
public class AccountCurrency {
	@EmbeddedId
	AccountCurrencyId accountCurrencyId;

	@Column(name = "balance")
	@NotNull
	private BigInteger balance;

	@Column(name = "lending_rate")
	@NotNull
	private BigInteger lendingRate;
}
