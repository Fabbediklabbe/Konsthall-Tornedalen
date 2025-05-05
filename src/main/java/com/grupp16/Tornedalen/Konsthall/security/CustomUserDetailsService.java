package com.grupp16.Tornedalen.Konsthall.security;

import com.grupp16.Tornedalen.Konsthall.SQL;
import com.grupp16.Tornedalen.Konsthall.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final SQL sql;

    public CustomUserDetailsService(SQL sql) {
        this.sql = sql;
        logger.info("CustomUserDetailsService created");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Trying to find user with email: {}", email);
        try {
            User user = sql.getUserByEmail(email);
            if (user == null) {
                logger.warn("No user found");
                throw new UsernameNotFoundException("User not found with email: " + email);
            }

            logger.info("   User found:");
            logger.info("   Email: {}", user.getEmail());
            logger.info("   Hashed password: {}", user.getPassword());

            return new CustomUserDetails(user);
        } catch (SQLException e) {
            logger.error("SQL-error when fetching user with email: {}", email, e);
            throw new UsernameNotFoundException("Could not fetch user with email: " + email, e);
        }
    }
}
