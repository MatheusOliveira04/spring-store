package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.requests.ItemSaleRequest;
import git.matheusoliveira04.api.store.models.dtos.responses.ItemSaleResponse;
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
@Entity(name = "tb_item_sale")
public class ItemSale {

    @Id
    @Setter
    @GeneratedValue
    private UUID id;

    @Setter
    @Min(value = 0)
    @Column(nullable = false)
    private Integer quantity;

    @Setter
    @Column(nullable = false)
    @DecimalMin(value = "0.1")
    private BigDecimal amount;

    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne
    private Product product;

    @JoinColumn(name = "sale_id", nullable = false)
    @ManyToOne
    private Sale sale;

    public ItemSale(ItemSaleRequest itemSaleRequest, Product product, Sale sale) {
        this.quantity = itemSaleRequest.quantity();
        this.amount = itemSaleRequest.amount();
        this.product = product;
        this.sale = sale;
    }

    public ItemSaleResponse toDtoResponse() {
        return new ItemSaleResponse(id, quantity, amount, product, sale.toDtoResponse());
    }
}
