package com.telugu.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private int mrpPrice;
    private int sellingPrice;
    private int discountPercentage;
    private int quantity;
    private String color;

    @ElementCollection
    private List<String> images = new ArrayList<>();

    private int numRating;

    @ManyToOne

    private Category category1;

    @ManyToOne
    private Category category2;

    @ManyToOne
    private Category category3;

    @ManyToOne
    private Seller seller;


    private LocalDateTime createdAt;

    private String sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    private String brand;
    private double weight; // in kilograms
    private String dimensions; // in cm (length x width x height)
}