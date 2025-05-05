package com.grupp16.Tornedalen.Konsthall.security;

import com.grupp16.Tornedalen.Konsthall.SQL;
import com.grupp16.Tornedalen.Konsthall.User;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final SQL sql;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomUserDetailsService(SQL sql, PasswordEncoder passwordEncoder) {
        this.sql = sql;
        this.passwordEncoder = passwordEncoder;
        System.out.println("✅ CustomUserDetailsService skapad");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("🔍 Försöker hitta användare med e-post: " + email);
        try {
            User user = sql.getUserByEmail(email);
            if (user == null) {
                System.out.println("❌ Ingen användare hittades");
                throw new UsernameNotFoundException("User not found with email: " + email);
            }

            System.out.println("✅ Användare hittad:");
            System.out.println("   E-post: " + user.getEmail());
            System.out.println("   Hashat lösenord: " + user.getPassword());

            // 🔐 TEST – kan tas bort senare
            String exempelLösenord = "test123";
            boolean match = passwordEncoder.matches(exempelLösenord, user.getPassword());
            System.out.println("🔐 Testar lösenord \"" + exempelLösenord + "\": matchar hash? " + match);

            return new CustomUserDetails(user);
        } catch (SQLException e) {
            System.out.println("🚨 SQL-fel: " + e.getMessage());
            throw new UsernameNotFoundException("Database error: " + e.getMessage());
        }
    }
}
