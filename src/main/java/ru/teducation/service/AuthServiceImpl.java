package ru.teducation.service;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.teducation.config.JwtUtil;
import ru.teducation.dto.AuthResponse;
import ru.teducation.dto.LoginRequest;
import ru.teducation.exception.AuthenticationException;
import ru.teducation.kafka.producer.UserCreatedEventProducer;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final UserCreatedEventProducer userCreatedEventProducer;

  public AuthResponse loginOrRegister(LoginRequest request) {
    Optional<User> userOpt = userRepository.findByName(request.getUsername());

    User user;

    if (userOpt.isPresent()) {
      user = userOpt.get();

      if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
        throw new AuthenticationException("Invalid password");
      }
    } else {
      user = buildNewUser(request);
      user = userRepository.save(user);
      userCreatedEventProducer.send(user.getId());
    }

    String token = jwtUtil.generateToken(user.getName(), user.getId());
    return new AuthResponse(token, user.getName());
  }

  private User buildNewUser(LoginRequest request) {
    return new User(
        request.getUsername(),
        passwordEncoder.encode(request.getPassword()),
        LocalDateTime.now(),
        LocalDateTime.now(),
        0,
        0);
  }
}
