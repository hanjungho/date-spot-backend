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
    private final JWTUtil jwtUtil;

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
        account.setRole("USER");

        accountRepository.save(account);
    }

    public AuthTokenDTO login(UserRequestDTO userRequestDTO) throws BadRequestException {
        if (userRequestDTO.username().isEmpty() || userRequestDTO.password().isEmpty()) {
            throw new BadRequestException("잘못된 입력!");
        }
        String username = userRequestDTO.username();

        String token = jwtUtil.createJwt(username, "USER");
        return new AuthTokenDTO(token);
    }
}
