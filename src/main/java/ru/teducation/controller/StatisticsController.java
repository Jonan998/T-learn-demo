package ru.teducation.controller;

import ru.teducation.dto.StatPointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.teducation.repository.CardsWordsRepository;
import ru.teducation.Security.UserPrincipal;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/learning/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final CardsWordsRepository cardsWordsRepository;
    // Статистика по месяцам за выбранный год
    @GetMapping("/year")
    public List<StatPointDto> getYearStats(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam int year) {
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59, 59);

        List<LocalDateTime> dates = cardsWordsRepository.findLearnedDates(user.getId(), start, end);

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

    // Статистика по дням для выбранного месяца
    @GetMapping("/month")
    public List<StatPointDto> getMonthStats(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam int year,
            @RequestParam int month) {

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<LocalDateTime> dates = cardsWordsRepository.findLearnedDates(user.getId(), start, end);

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
