package com.brodep.cloudstoragepetproject.mapper;

import com.brodep.cloudstoragepetproject.dto.request.UserRequest;
import com.brodep.cloudstoragepetproject.dto.response.UserResponse;
import com.brodep.cloudstoragepetproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User userRequestToUser(UserRequest userRequest){
        return User.builder()
                .username(userRequest.username())
                .password(passwordEncoder.encode(userRequest.password()))
                .build();
    }

    public UserResponse userToUserResponse(User user) {
        return new UserResponse(user.getUsername());
    }
}
