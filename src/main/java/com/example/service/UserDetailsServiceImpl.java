package com.example.service;

import com.example.Security.UserPrincipal;
import com.example.model.User;
import com.example.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
