package com.github.hanjungho.datespotbackend.controller;

import com.github.hanjungho.datespotbackend.dto.AuthTokenDTO;
import com.github.hanjungho.datespotbackend.dto.UserRequestDTO;
import com.github.hanjungho.datespotbackend.service.AccountService;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;

    @PostMapping("/api/auth/join")
    public ResponseEntity<String> joinProcess(UserRequestDTO userRequestDTO) {

        accountService.join(userRequestDTO);

        return ResponseEntity.ok("ok~~");
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthTokenDTO> login(@RequestBody UserRequestDTO dto) throws BadRequestException {
        AuthTokenDTO tokenDTO = accountService.login(dto);
        return ResponseEntity.ok(tokenDTO);
    }
}
