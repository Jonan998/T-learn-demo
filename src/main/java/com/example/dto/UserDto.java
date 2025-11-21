package com.example.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String name;
    private LocalDate createdAtNew;
    private LocalDate createdAtRepeat;
    private int limitNew;
    private int limitRepeat;
}