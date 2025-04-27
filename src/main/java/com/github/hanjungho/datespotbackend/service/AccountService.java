package com.github.hanjungho.datespotbackend.service;

import com.github.hanjungho.datespotbackend.dto.AuthTokenDTO;
import com.github.hanjungho.datespotbackend.dto.UserRequestDTO;
import com.github.hanjungho.datespotbackend.entity.AccountEntity;
import com.github.hanjungho.datespotbackend.jwt.JWTUtil;
import com.github.hanjungho.datespotbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(UserRequestDTO userRequestDTO) {

        String username = userRequestDTO.username();
        String password = userRequestDTO.password();

        boolean isExist = accountRepository.existsByUsername(username);

        if (isExist) {
            return;
        }

        AccountEntity account = new AccountEntity();
        account.setUsername(username);
        account.setPassword(bCryptPasswordEncoder.encode(password));
        account.setRole("ROLE_USER");

        accountRepository.save(account);
    }
}
