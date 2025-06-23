package com.telugu.ecommerce.model;


import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;

    private int totalitem;

    private int totalMrpPrice;

    private String couponCode;

    private String discountPercentage;


    public double getTotal() {
        return cartItems.stream().mapToDouble(CartItem::getTotal).sum();
    }

        public void setTotal(double discountedTotal) {
        this.totalSellingPrice = discountedTotal;
    }

}
