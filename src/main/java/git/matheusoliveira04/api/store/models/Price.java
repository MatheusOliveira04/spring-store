package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.PriceRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_price")
public class Price {

    @Id
    @Setter
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @DecimalMin(value = "0.0")
    private BigDecimal costPrice;

    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    private BigDecimal salePrice;

    private BigDecimal profit;

    private Double profitMargin;

    public Price(BigDecimal costPrice, BigDecimal salePrice) {
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.calculateProfit();
        this.calculateProfitMargin();
    }

    public Price(PriceRequest priceRequest) {
        this(priceRequest.costPrice(), priceRequest.salePrice());
    }

    public void calculateProfitMargin() {
        this.profitMargin = profit.divide(costPrice, 2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
    }

    private void calculateProfit() {
        if (costPrice != null && salePrice != null) {
            this.profit = salePrice.subtract(costPrice);
        }
    }

}
