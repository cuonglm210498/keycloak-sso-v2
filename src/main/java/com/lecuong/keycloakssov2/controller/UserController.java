package com.lecuong.keycloakssov2.controller;

import com.lecuong.keycloakssov2.entity.User;
import com.lecuong.keycloakssov2.modal.response.BaseResponse;
import com.lecuong.keycloakssov2.modal.response.Payload;
import com.lecuong.keycloakssov2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Payload payload;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public ResponseEntity<BaseResponse<User>> findById(@PathVariable Long id) {
        System.out.println(payload.getName());
        return ResponseEntity.ok(BaseResponse.ofSuccess(userService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<BaseResponse<Void>> save(@RequestBody User user) {
        System.out.println(payload.getName());
        userService.save(user);
        return ResponseEntity.ok(BaseResponse.ofSuccess(null));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
//    @RolesAllowed({"admin"})
    public ResponseEntity<String> getUserInfoAdmin() {
        return ResponseEntity.ok("admin");
    }

    @GetMapping("/user-and-admin")
    @PreAuthorize("hasRole('user') or hasRole('admin')")
    public ResponseEntity<String> getUserInfo() {
        return ResponseEntity.ok("user and admin");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<String> getUserInfoUser() {
        return ResponseEntity.ok("user");
    }
}
