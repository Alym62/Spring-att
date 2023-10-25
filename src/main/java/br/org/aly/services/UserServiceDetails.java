package br.org.aly.services;

import br.org.aly.repository.DevUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceDetails implements UserDetailsService {
    private final DevUserRepository devUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.of(devUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário do DB não encontrado! 🚨"));
    }
}
