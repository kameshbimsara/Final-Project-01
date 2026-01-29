package lk.Project.smart_biz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private String location;
    private LocalDate regDate;
    private String ownerName;
    private String ownerContact;
    private String username;
    private String password;
    private Integer status;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers;
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orders> orders;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin")
    private Admin admin;
    @OneToMany(mappedBy = "business")
    private List<Product> products;
    @OneToMany(mappedBy = "business")
    private List<Payments> payments;
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Batch> batches;

    public Business(String name) {
        this.name = name;
    }

    public Business(Integer id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.regDate = LocalDate.now();
    }
}
