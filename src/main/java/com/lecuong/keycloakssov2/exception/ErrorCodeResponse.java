package com.lecuong.keycloakssov2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorCodeResponse {

    private String code;
    private String message;
    private HttpStatus status;
}
