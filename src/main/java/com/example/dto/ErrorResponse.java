package com.example.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String code;
    private String message;
}
