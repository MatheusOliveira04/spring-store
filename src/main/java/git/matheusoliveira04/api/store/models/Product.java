package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.ProductRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_product")
public class Product {

    @Id
    @Setter
    @Getter
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Pattern(regexp = "\\d+")
    @Column(unique = true)
    private String codeBar;

    @Column(nullable = false)
    private Integer stock = 0;

    @JoinColumn(name = "price_id", nullable = false, unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    public Product(ProductRequest productRequest, Price price) {
        this(null, productRequest.name(), productRequest.codeBar(), productRequest.stock(), price);
    }
}
