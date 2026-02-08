package ru.teducation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teducation.dto.StatPointDto;
import ru.teducation.repository.CardsWordsRepository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{

    private final CardsWordsRepository cardsWordsRepository;

    @Override
    public List<StatPointDto> getYearStats(int userId, int year) {

        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        List<LocalDateTime> dates = cardsWordsRepository.findLearnedDates(userId, start, end);

        Map<Integer, Long> monthlyCounts = dates.stream()
                .collect(Collectors.groupingBy(
                        LocalDateTime::getMonthValue,
                        Collectors.counting()
                ));

        List<StatPointDto> result = new ArrayList<>();
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        for (int i = 1; i <= 12; i++) {
            long countInMonth = monthlyCounts.getOrDefault(i, 0L);
            result.add(new StatPointDto(monthNames[i - 1], countInMonth));
        }
        return result;
    }

    @Override
    public List<StatPointDto> getMonthStats(int userId, int year, int month) {

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<LocalDateTime> dates = cardsWordsRepository.findLearnedDates(userId, start, end);

        Map<Integer, Long> dailyCounts = dates.stream()
                .collect(Collectors.groupingBy(
                        LocalDateTime::getDayOfMonth,
                        Collectors.counting()
                ));

        List<StatPointDto> result = new ArrayList<>();

        for(int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
            long countToday = dailyCounts.getOrDefault(day, 0L);
            result.add(new StatPointDto(String.format("%02d.%02d", day, month), countToday));
        }
        return result;
    }
}
