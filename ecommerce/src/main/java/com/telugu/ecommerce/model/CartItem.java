package com.telugu.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;



@Entity


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;


    @ManyToOne
    private Product product;

    private int size;


    private int quantity=1 ;

    private Integer mrpPrice;

    private Integer sellingPrice;

    private Long userId;


    public double getTotal() {
        return sellingPrice * quantity;
    }

    public void getMrpPrice(int i) {
    }
}
