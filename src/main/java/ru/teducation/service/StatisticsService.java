package ru.teducation.service;

import java.util.List;
import ru.teducation.dto.StatPointDto;

public interface StatisticsService {
  List<StatPointDto> getYearStats(int userId, int year);

  List<StatPointDto> getMonthStats(int userId, int year, int month);
}
