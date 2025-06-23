package com.telugu.ecommerce.model;

import com.telugu.ecommerce.domain.PaymentMethod;
import com.telugu.ecommerce.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private Long amount;

    private String paymentLinkId;

    private PaymentOrderStatus paymentOrderStatus=PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<Order> Orders = new HashSet<>();

}
