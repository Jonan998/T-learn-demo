package ru.teducation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewDeckDto {
  private Integer id;
  private Integer studyLvl;
  private String engLang;
  private String rusLang;
  private String transcription;
  @JsonIgnore private Integer dictionaryId;
}
