package ru.teducation.service;

import ru.teducation.dto.StatPointDto;
import java.util.List;

public interface StatisticsService {
    List<StatPointDto> getYearStats(int userId, int year);
    List<StatPointDto> getMonthStats(int userId, int year, int month);
}
