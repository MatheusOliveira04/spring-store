package git.matheusoliveira04.api.store.models;

import jakarta.persistence.*;
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
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Pattern(regexp = "\\d+")
    private String codeBar;

    private String unitType;

    @Column(nullable = false)
    private Integer stock = 0;

    @JoinColumn(name = "price_id", nullable = false)
    @OneToOne
    private Price price;
}
