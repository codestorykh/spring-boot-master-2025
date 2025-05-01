package com.codestorykh.object.service;

import com.codestorykh.object.dto.AddressDTO;
import com.codestorykh.object.dto.UserDTO;
import com.codestorykh.object.manual.UserMapper;
import com.codestorykh.object.mapstruct.UserMapperUsingMapStruct;
import com.codestorykh.object.model.Address;
import com.codestorykh.object.model.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

    private final ModelMapper modelMapper;
    private final UserMapperUsingMapStruct userMapperUsingMapStruct;

    public UserService(ModelMapper modelMapper,
                       UserMapperUsingMapStruct userMapperUsingMapStruct) {
        this.modelMapper = modelMapper;
        this.userMapperUsingMapStruct = userMapperUsingMapStruct;
    }

    //Convert User to UserDTO by using manual mapping
    public UserDTO manualMapUserToUserDTO(User user) {
        return UserMapper.toUserDTO(user);
    }

    //Convert UserDTO to User by using Spring BeanUtils mapping
    public UserDTO userToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);

        // Manually map the addresses
        List<AddressDTO> addressDTOs = new ArrayList<>();
        for(Address address : user.getAddresses()) {
            AddressDTO addressDTO = new AddressDTO();
            BeanUtils.copyProperties(address, addressDTO);
           addressDTOs.add(addressDTO);
        }
        dto.setAddresses(addressDTOs);
        return dto;
    }

   //Convert UserDTO to User by using ModelMapper
    public UserDTO modelMapperUserToUserDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        //method in ModelMapper checks whether all properties in the source and destination types (e.g., entity and DTO) are properly mapped.
        //  modelMapper.validate();
        return userDTO;
    }

    //Convert UserDTO to User by using MapStruct
    public UserDTO mapStructUserToUserDTO(User user) {
        return userMapperUsingMapStruct.mapUserToUserDTO(user);
    }

    //Convert UserDTO to User by using Orika
    public UserDTO orikaMapUserToUserDTO(User user) {
       //return userMapperUsingOrika.orikaMapUserToUserDTO(user);
    }
}
