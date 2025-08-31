package com.brodep.cloudstoragepetproject.service;

import com.brodep.cloudstoragepetproject.entity.User;
import com.brodep.cloudstoragepetproject.exeption.ResourceNotFoundException;
import com.brodep.cloudstoragepetproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean existsByUserName(String username){
        return userRepository.existsByUsername(username);
    }

    public User save (User user){
        return userRepository.save(user);
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new ResourceNotFoundException(
                        "User with username %s not found".formatted(username)
                ));
    }

    public UserDetailsService userDetailsService() {
        return this::findByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByUsername(username);
    }
}
