package ru.teducation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
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
  private LocalDateTime createdAtNew;
  private LocalDateTime createdAtRepeat;
  private Integer limitNew;
  private Integer limitRepeat;
  private String password;
  private String newPassword;

  public UserDto(Integer limitNew, Integer limitRepeat) {
    this.limitNew = limitNew;
    this.limitRepeat = limitRepeat;
  }

  public UserDto(
      String name, Integer limitNew, Integer limitRepeat, String password, String newPassword) {
    this.name = name;
    this.limitNew = limitNew;
    this.limitRepeat = limitRepeat;
    this.password = password;
    this.newPassword = newPassword;
  }
}
