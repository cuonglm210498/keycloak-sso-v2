package com.lecuong.keycloakssov2.service.impl;

import com.lecuong.keycloakssov2.entity.User;
import com.lecuong.keycloakssov2.repository.UserRepository;
import com.lecuong.keycloakssov2.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
