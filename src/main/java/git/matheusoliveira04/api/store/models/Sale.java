package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.SaleRequest;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "tb_sale")
public class Sale {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @SequenceGenerator(name = "sale_code_seq", sequenceName = "sale_code_seq", allocationSize = 1)
    @Column(unique = true)
    private Long code;

    @Column(nullable = false)
    private String description;

    @Setter
    @Column(nullable = false)
    @DecimalMin(value = "0.0")
    private BigDecimal valueTotal = BigDecimal.ZERO;

    @Setter
    @Column(nullable = false)
    @Min(value = 0)
    private Integer quantityTotal = 0;

    @CreatedDate
    private LocalDateTime dateTime;

    @JoinColumn(name = "employee_id", nullable = false)
    @ManyToOne
    private Employee employee;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ItemSale> itemSales;

    public Sale(SaleRequest saleRequest, Employee employee, Client client) {
        this.description = saleRequest.description();
        this.employee = employee;
        this.client = client;
    }


}

