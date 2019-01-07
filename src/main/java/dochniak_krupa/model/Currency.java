package dochniak_krupa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity
public class Currency {

    @Id
    @Column(columnDefinition="char(3)")
    @Size(min=3,max=3)
    private String iso;

    @NotNull
    @Size(max=30)
    private String name;

    @Column(name = "exchange_to_dollar")
    @NotNull
    private BigInteger exchangeToDollar;

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getExchangeToDollar() {
        return exchangeToDollar;
    }

    public void setExchangeToDollar(BigInteger exchangeToDollar) {
        this.exchangeToDollar = exchangeToDollar;
    }
}
