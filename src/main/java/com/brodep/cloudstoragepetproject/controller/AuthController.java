package com.brodep.cloudstoragepetproject.controller;

import com.brodep.cloudstoragepetproject.dto.request.UserRequest;
import com.brodep.cloudstoragepetproject.dto.response.UserResponse;
import com.brodep.cloudstoragepetproject.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Аутентификация")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Регистрация пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("sign-up")
    public UserResponse signUp(@RequestBody UserRequest userRequest) {
        return authService.signUp(userRequest);
    }

    @Operation(summary = "Авторизация пользователя")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("sign-in")
    public UserResponse signIn(@RequestBody UserRequest userRequest, HttpServletRequest request) throws ServletException {
        return authService.signIn(userRequest, request);
    }

    @Operation(summary = "Логаут пользователя")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("auth/sign-out")
    public void signOut(HttpServletRequest request) {
        authService.signOut(request);
    }

}
