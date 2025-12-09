package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
  private Integer id;
  private String name;
  private LocalDate createdAtNew;
  private LocalDate createdAtRepeat;
  private Integer limitNew;
  private Integer limitRepeat;

  public UserDto(Integer limitNew, Integer limitRepeat) {
    this.limitNew = limitNew;
    this.limitRepeat = limitRepeat;
  }

  public UserDto(String name, Integer limitNew, Integer limitRepeat) {
    this.name = name;
    this.limitNew = limitNew;
    this.limitRepeat = limitRepeat;
  }
}
