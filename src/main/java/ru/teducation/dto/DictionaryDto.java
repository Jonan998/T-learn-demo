package ru.teducation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryDto {
  private Integer id;
  private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String description;

  private String language;

  public DictionaryDto(Integer id, String name, String language) {
    this.id = id;
    this.name = name;
    this.language = language;
  }
}
