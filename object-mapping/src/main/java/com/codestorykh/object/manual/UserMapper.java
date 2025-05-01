package com.codestorykh.object.manual;

import com.codestorykh.object.model.Address;
import com.codestorykh.object.dto.AddressDTO;
import com.codestorykh.object.model.User;
import com.codestorykh.object.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user .getPhone());
        dto.setPassword(user.getPassword());

        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address : user.getAddresses()) {
            var addressDTO = new AddressDTO();
            addressDTO.setId(address.getId());
            addressDTO.setCountry(address.getCountry());
            addressDTO.setCity(address.getCity());
            addressDTO.setProvince(address.getProvince());
            addressDTO.setDistrict(address.getDistrict());
            addressDTO.setCommune(address.getCommune());
            addressDTO.setVillage(address.getVillage());
            addressDTO.setStreet(address.getStreet());
            addressDTO.setHouseNumber(address.getHouseNumber());

            addressDTOS.add(addressDTO);
        }
        dto.setAddresses(addressDTOS);
        return dto;
    }
}
