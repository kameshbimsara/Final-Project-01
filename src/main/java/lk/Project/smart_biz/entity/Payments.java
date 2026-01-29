package lk.Project.smart_biz.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", nullable = false)
    private Business business;
}
