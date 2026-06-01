package com.website.FoodOrder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne        // This will put fk automatically based on who is the parent one.
    private User owner;

    private String name;

    private String description;

    private String cuisineType;      // "Italian", "Chinese", "Mexican", etc.

    @OneToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;

    @OneToMany(mappedBy = "restaurant", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    private LocalDateTime registrationDate;

    private boolean open;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foods = new ArrayList<>();

}
