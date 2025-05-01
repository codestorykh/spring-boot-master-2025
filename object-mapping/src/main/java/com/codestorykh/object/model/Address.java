package com.codestorykh.object.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    private Long id;
    private String country;
    private String city;
    private String province;
    private String district;
    private String commune;
    private String village;
    private String street;
    private String houseNumber;
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
