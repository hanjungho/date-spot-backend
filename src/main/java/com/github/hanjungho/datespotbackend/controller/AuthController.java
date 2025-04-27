package com.github.hanjungho.datespotbackend.controller;

import com.github.hanjungho.datespotbackend.dto.AuthTokenDTO;
import com.github.hanjungho.datespotbackend.dto.CustomUserDetails;
import com.github.hanjungho.datespotbackend.dto.UserRequestDTO;
import com.github.hanjungho.datespotbackend.jwt.JWTUtil;
import com.github.hanjungho.datespotbackend.service.AccountService;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @PostMapping("/api/auth/join")
    public ResponseEntity<String> joinProcess(UserRequestDTO userRequestDTO) {

        accountService.join(userRequestDTO);

        return ResponseEntity.ok("ok~~");
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthTokenDTO> login(@RequestBody UserRequestDTO dto) throws BadRequestException {
        try {
            // Spring Security의 인증 메커니즘 사용
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
            );

            // 인증 성공 후 사용자 정보 가져오기
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // 토큰 생성
            String role = userDetails.getAuthorities().iterator().next().getAuthority();
            String token = jwtUtil.createJwt(userDetails.getUsername(), role);

            return ResponseEntity.ok(new AuthTokenDTO(token));
        } catch (AuthenticationException e) {
            throw new BadRequestException("인증 실패: " + e.getMessage());
        }
    }
}
