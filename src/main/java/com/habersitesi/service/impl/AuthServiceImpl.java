package com.habersitesi.service.impl;

import com.habersitesi.dto.LoginRequest;
import com.habersitesi.dto.RegisterRequest;
import com.habersitesi.exception.YetkisizIslemException;
import com.habersitesi.model.*;
import com.habersitesi.repository.*;
import com.habersitesi.security.JwtTokenProvider;
import com.habersitesi.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;
    private final JwtTokenProvider jwtProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (kullaniciRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu e-posta zaten kayıtlı.");
        }

        Rol rol = rolRepository.findByAd(Rol.RolTipi.valueOf(request.getRol()))
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + request.getRol()));

        Kullanici k = new Kullanici();
        k.setEmail(request.getEmail());
        k.setSifre(passwordEncoder.encode(request.getSifre()));
        k.setAdSoyad(request.getAdSoyad());
        k.setRoller(Set.of(rol));

        kullaniciRepository.save(k);
    }

    @Override
    public String login(LoginRequest request) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSifre())
            );
        } catch (AuthenticationException e) {
            throw new YetkisizIslemException("Giriş bilgileri hatalı.");
        }

        return jwtProvider.generateToken(request.getEmail());
    }

}
