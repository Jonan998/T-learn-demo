package ru.teducation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.teducation.Security.UserPrincipal;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user =
        userRepository
            .findByName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

    return new UserPrincipal(
        user.getId(), user.getName(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }

  public UserDetails loadUserById(int userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

    return new UserPrincipal(
        user.getId(), user.getName(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
  }
}
