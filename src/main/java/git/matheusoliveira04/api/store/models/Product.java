package git.matheusoliveira04.api.store.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
}
