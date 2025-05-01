package com.codestorykh.object.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {

    private Long id;
    private String name;
    private String customerName;
    private String email;
    private String password;
    private String phone;
    private String createdAt;

    private List<AddressDTO> addresses;


}
