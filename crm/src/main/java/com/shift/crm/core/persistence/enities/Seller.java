package com.shift.crm.core.persistence.enities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contactInfo;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime registrationDate;
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
    private boolean isActive = true;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Transaction> transactions = new ArrayList<>();
}
