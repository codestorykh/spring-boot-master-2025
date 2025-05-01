package com.codestorykh.object.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {
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
}