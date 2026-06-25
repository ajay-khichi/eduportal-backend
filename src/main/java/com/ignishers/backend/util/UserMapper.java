package com.ignishers.backend.util;

import com.ignishers.backend.model.user.User;
import com.ignishers.backend.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
