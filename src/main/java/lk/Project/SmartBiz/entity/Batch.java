package lk.Project.SmartBiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate manufactureDate;
    private LocalDate expireDate;
    private Double unitPrice;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private BizSuppler supplier;
}
