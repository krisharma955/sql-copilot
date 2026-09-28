package com.K955.sql_copilot.mapper;

import com.K955.sql_copilot.dtos.Auth.UserProfileResponse;
import com.K955.sql_copilot.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    UserProfileResponse toUserProfileResponse(User user);

}
