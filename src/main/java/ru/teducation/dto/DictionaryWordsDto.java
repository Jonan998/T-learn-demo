package ru.teducation.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryWordsDto {
  private Integer id;
  private Integer wordId;
  private Integer dictionaryId;
}
