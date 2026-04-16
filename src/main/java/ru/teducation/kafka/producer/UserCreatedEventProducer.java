package ru.teducation.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.teducation.kafka.event.UserCreatedEvent;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCreatedEventProducer {

  private static final String TOPIC = "user-created";

  private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

  public void send(Integer userId) {
    kafkaTemplate.send(TOPIC, String.valueOf(userId), new UserCreatedEvent(userId));
    log.info("Отправлено Kafka событие user-created для userId={}", userId);
  }
}
