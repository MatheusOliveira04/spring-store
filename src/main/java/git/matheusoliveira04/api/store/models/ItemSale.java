package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.ItemSaleRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "/tb_item_sale")
public class ItemSale {

    @Id
    @Setter
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer quantity;

    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    private BigDecimal value;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @JoinColumn(name = "sale_id", nullable = false)
    @ManyToOne
    private Sale sale;

    public ItemSale(ItemSaleRequest itemSaleRequest, Product product, Sale sale) {
        this.quantity = itemSaleRequest.quantity();
        this.value = itemSaleRequest.value();
        this.product = product;
        this.sale = sale;
    }
}
