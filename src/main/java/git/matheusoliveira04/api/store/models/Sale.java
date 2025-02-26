package git.matheusoliveira04.api.store.models;

import git.matheusoliveira04.api.store.models.dtos.requests.SaleRequest;
import git.matheusoliveira04.api.store.models.dtos.responses.SaleResponse;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @CreationTimestamp
    private LocalDateTime dateTime;

    @JoinColumn(name = "employee_id", nullable = false)
    @ManyToOne
    private Employee employee;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private Client client;

    public Sale(SaleRequest saleRequest, Employee employee, Client client) {
        this.description = saleRequest.description();
        this.employee = employee;
        this.client = client;
    }

    public SaleResponse toDtoResponse() {
        return new SaleResponse(id, description, valueTotal, quantityTotal, dateTime, employee, client.toDtoResponse());
    }
}

