package git.matheusoliveira04.api.store.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_entity")
public class Price {

    @Id
    @Setter
    @GeneratedValue
    private UUID id;

    private BigDecimal costPrice = BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal salePrice;

    @NotNull
    private BigDecimal profit;

    @NotNull
    private Double profitMargin;

    public Price(BigDecimal costPrice, BigDecimal salePrice) {
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.calculate();
    }

    public void calculate() {
        if (costPrice != null && salePrice != null) {
            this.profit = salePrice.subtract(costPrice);

            if (salePrice.compareTo(BigDecimal.ZERO) > 0) {
                this.profitMargin = profit.divide(salePrice, 2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
            } else {
                this.profitMargin = 0.0;
            }
        }
    }

}
