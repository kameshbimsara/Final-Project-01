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
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders;

    public Business(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
