package ru.teducation.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.teducation.kafka.event.UserCreatedEvent;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedConsumer {

  private static final int DEFAULT_LIMIT_NEW = 10;
  private static final int DEFAULT_LIMIT_REPEAT = 20;

  private final UserRepository userRepository;

  @KafkaListener(topics = "user-created", groupId = "user-init-group")
  public void handle(UserCreatedEvent event) {
    log.info("Получено Kafka событие user-created для userId={}", event.getUserId());

    User user =
        userRepository
            .findById(event.getUserId())
            .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

    if (user.getLimitNew() == 0) {
      user.setLimitNew(DEFAULT_LIMIT_NEW);
    }

    if (user.getLimitRepeat() == 0) {
      user.setLimitRepeat(DEFAULT_LIMIT_REPEAT);
    }

    userRepository.save(user);

    log.info(
        "Пользователь {} инициализирован: limitNew={}, limitRepeat={}",
        user.getId(),
        user.getLimitNew(),
        user.getLimitRepeat());
  }
}
