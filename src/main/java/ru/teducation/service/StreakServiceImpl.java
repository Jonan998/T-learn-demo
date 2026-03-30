package ru.teducation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void updateStreak(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();
        LocalDate lastActivity = user.getLastActivityDate();

        if (lastActivity != null && lastActivity.equals(today)) {
            return;
        }

        if (lastActivity != null && lastActivity.equals(today.minusDays(1))) {
            user.setCurrentStreak(user.getCurrentStreak() + 1);
        }
        else {
            user.setCurrentStreak(1);
        }

        user.setLastActivityDate(today);
        userRepository.save(user);
    }
}