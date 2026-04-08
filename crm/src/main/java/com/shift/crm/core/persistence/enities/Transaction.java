package com.shift.crm.core.persistence.enities;

import com.shift.crm.core.persistence.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

}
