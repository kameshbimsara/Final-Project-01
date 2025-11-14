package lk.Project.SmartBiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String type;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private BizOwner owner;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers;

    public Business(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
