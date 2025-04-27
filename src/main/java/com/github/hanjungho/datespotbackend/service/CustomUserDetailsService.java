package com.github.hanjungho.datespotbackend.service;

import com.github.hanjungho.datespotbackend.dto.CustomUserDetails;
import com.github.hanjungho.datespotbackend.entity.AccountEntity;
import com.github.hanjungho.datespotbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountEntity account = accountRepository.findByUsername(username);

        if (account != null) {

            return new CustomUserDetails(account);
        }

        return null;
    }
}
