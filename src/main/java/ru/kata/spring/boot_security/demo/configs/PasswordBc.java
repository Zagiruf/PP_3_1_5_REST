package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordBc {
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
