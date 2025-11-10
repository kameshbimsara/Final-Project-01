package lk.Project.SmartBiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "biz_suppler")
public class BizSuppler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;
    private String contactNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    // ✅ Custom constructor (without products list)
    public BizSuppler(Integer id, String companyName, String contactNo, Business business) {
        this.id = id;
        this.companyName = companyName;
        this.contactNo = contactNo;
        this.business = business;
    }
}
