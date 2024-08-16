package lma.controller;

import jakarta.servlet.http.HttpServletRequest;
import lma.dto.JwtResponse;
import lma.dto.UserReadDto;
import lma.entity.TokenPair;
import lma.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody UserReadDto userReadDto, HttpServletRequest request) {
        TokenPair registerTokens = authService.register(userReadDto, request.getRemoteAddr());
        JwtResponse token = new JwtResponse(registerTokens.getAccessToken(), registerTokens.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserReadDto user, HttpServletRequest request) {
        TokenPair loginTokens = authService.login(user, request.getRemoteAddr());
        JwtResponse token = new JwtResponse(loginTokens.getAccessToken(), loginTokens.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestParam String refreshToken, HttpServletRequest request) {
        TokenPair refreshTokenPair = authService.refresh(refreshToken, request.getRemoteAddr());
        JwtResponse token = new JwtResponse(refreshTokenPair.getAccessToken(), refreshTokenPair.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
