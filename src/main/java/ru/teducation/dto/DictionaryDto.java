package ru.teducation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryDto {
  private Integer id;

  @NotBlank private String name;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String description;

  private String language;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Integer ownerId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Boolean isPublic;

  public DictionaryDto(Integer id, String name, String language) {
    this.id = id;
    this.name = name;
    this.language = language;
  }
}
