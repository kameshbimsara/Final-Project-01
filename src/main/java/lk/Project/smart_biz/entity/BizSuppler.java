package lk.Project.smart_biz.entity;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;

    @OneToMany(mappedBy = "bizSuppler",
            cascade = CascadeType.ALL)
    private List<Batch> batch;
}
