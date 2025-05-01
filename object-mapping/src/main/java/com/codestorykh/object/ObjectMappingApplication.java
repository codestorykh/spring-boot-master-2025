package com.codestorykh.object;

import com.codestorykh.object.dto.UserDTO;
import com.codestorykh.object.model.Address;
import com.codestorykh.object.model.User;
import com.codestorykh.object.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ObjectMappingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObjectMappingApplication.class, args);
    }

    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setId(1L);
        user.setName("CodeStoryKH");
        user.setPhone("08925****");
        user.setEmail("codestorykh@gmail.com");
        user.setPassword("password@123");
        user.setCreatedAt(new Date());

        var address = new Address();
        address.setId(1L);
        address.setCountry("Cambodia");
        address.setCity("Phnom Penh");
        address.setProvince("Phnom Penh");
        address.setDistrict("Chamkar Mon");
        address.setCommune("Tonle Bassac");
        address.setVillage("Tonle Bassac");
        address.setStreet("Street 271");
        address.setHouseNumber("123");
        address.setZipCode("1234");

        user.setAddresses(List.of(address));

        UserDTO manualMapUserToUserDTO = userService.manualMapUserToUserDTO(user);
        System.out.println(manualMapUserToUserDTO.getName());

        UserDTO modelMapperUserToUserDTO = userService.modelMapperUserToUserDTO(user);
        System.out.println(modelMapperUserToUserDTO.getPassword());

        UserDTO beanUtilsUserToUserDTO = userService.userToUserDTO(user);
        System.out.println(beanUtilsUserToUserDTO.getName());

        UserDTO mapStructUserToUserDTO = userService.mapStructUserToUserDTO(user);
        System.out.println(mapStructUserToUserDTO.getName());

        UserDTO orikaUserToUserDTO = userService.orikaMapUserToUserDTO(user);
        System.out.println(orikaUserToUserDTO.getName());
    }
}
