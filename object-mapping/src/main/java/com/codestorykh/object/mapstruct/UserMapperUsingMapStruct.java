package com.codestorykh.object.mapstruct;

import com.codestorykh.object.dto.UserDTO;
import com.codestorykh.object.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapperUsingMapStruct {

    UserMapperUsingMapStruct INSTANCE = Mappers
            .getMapper(UserMapperUsingMapStruct.class);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "user.name", target = "customerName")
    UserDTO mapUserToUserDTO(User user);
}
