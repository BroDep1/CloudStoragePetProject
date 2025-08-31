package com.brodep.cloudstoragepetproject.service;

import com.brodep.cloudstoragepetproject.dto.request.UserRequest;
import com.brodep.cloudstoragepetproject.dto.response.UserResponse;
import com.brodep.cloudstoragepetproject.exeption.UsernameIsUnavailableException;
import com.brodep.cloudstoragepetproject.exeption.ValidationException;
import com.brodep.cloudstoragepetproject.mapper.UserMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserResponse signIn(UserRequest userRequest, HttpServletRequest request) throws ServletException {
        request.getSession().invalidate();
        request.login(
                userRequest.username(),
                userRequest.password()
        );
        return new UserResponse(userRequest.username());
    }

    public UserResponse signUp(UserRequest userRequest){
        log.info("Пользователь {} начал регистрацию", userRequest.username());
        if (userService.existsByUserName(userRequest.username())) {
            log.error("Пользователь {} неуспешно завершил регистрацию: юзернейм недоступен", userRequest.username());
            throw new UsernameIsUnavailableException("Username %s is unavailable"
                    .formatted(userRequest.username()));
        }
        if (userRequest.username().length() < 3) {
            log.error("Пользователь {} неуспешно завершил регистрацию: короткий юзернейм", userRequest.username());
            throw new ValidationException("Username %s is too short"
                    .formatted(userRequest.username()));
        }
        var user = userService.save(
                userMapper.userRequestToUser(userRequest));
        log.info("Пользователь {} успешно завершил регистрацию", userRequest.username());

        return userMapper.userToUserResponse(user);
    }


    public void signOut(HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
