package com.habersitesi.security;

import com.habersitesi.model.Kullanici;
import com.habersitesi.repository.KullaniciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));

        return User.builder()
                .username(kullanici.getEmail())
                .password(kullanici.getSifre())
                .roles(kullanici.getRoller().stream().map(r -> r.getAd().name()).toArray(String[]::new))
                .build();
    }
}
