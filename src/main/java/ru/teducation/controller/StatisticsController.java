package ru.teducation.controller;

import ru.teducation.dto.StatPointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.service.StatisticsService;
import java.util.List;

@RestController
@RequestMapping("/learning/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/year")
    public List<StatPointDto> getYearStats(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam int year) {
        return statisticsService.getYearStats(user.getId(), year);
    }

    @GetMapping("/month")
    public List<StatPointDto> getMonthStats(
            @AuthenticationPrincipal UserPrincipal user,
            @RequestParam int year,
            @RequestParam int month) {
        return statisticsService.getMonthStats(user.getId(), year, month);
    }
}
