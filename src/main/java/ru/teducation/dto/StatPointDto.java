package ru.teducation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatPointDto {
    private String label;
    private Long value;
}
