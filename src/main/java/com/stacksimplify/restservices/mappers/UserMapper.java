package com.stacksimplify.restservices.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.stacksimplify.restservices.dtos.UserMsDtos;
import com.stacksimplify.restservices.entites.User;

@Mapper(componentModel = "Spring", uses = {UserMsDtos.class})
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	//User to UserMsDto
	@Mappings({
	@Mapping(source = "email", target = "emailaddress"),
	@Mapping(source = "role", target = "rolename")
	
	})
	UserMsDtos userToUserMsDto(User user);
	
	//List<User> to List<UserMsDto>
	List<UserMsDtos> usersToUserMsDtos(List<User> users);
}
