package lk.Project.smart_biz.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private Integer totalAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @JoinColumn(name = "business_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Business business;
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetails> order_details;
}
