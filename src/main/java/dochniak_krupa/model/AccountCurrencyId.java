package dochniak_krupa.model;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class AccountCurrencyId implements Serializable {
    @ManyToOne
    private Client client;

    @ManyToOne
    private Currency currency;

    public AccountCurrencyId(Client client, Currency currency) {
        this.client = client;
        this.currency = currency;
    }

    public AccountCurrencyId() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountCurrencyId that = (AccountCurrencyId) o;

        if (!client.equals(that.client)) return false;
        return currency.equals(that.currency);
    }

    @Override
    public int hashCode() {
        int result = client.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
}
