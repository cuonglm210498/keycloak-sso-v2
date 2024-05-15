package com.lecuong.keycloakssov2.service;

import com.lecuong.keycloakssov2.entity.User;

public interface UserService {

    User findById(Long id);

    void save(User user);
}
