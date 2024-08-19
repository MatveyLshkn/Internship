package lma.controller;

import jakarta.servlet.http.HttpServletRequest;
import lma.dto.JwtResponse;
import lma.dto.RefreshTokenDto;
import lma.dto.UserReadDto;
import lma.entity.TokenPair;
import lma.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static lma.constants.CommonConstants.LOGIN_ENDPOINT;
import static lma.constants.CommonConstants.REFRESH_ENDPOINT;
import static lma.constants.CommonConstants.REGISTER_ENDPOINT;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER_ENDPOINT)
    public ResponseEntity<JwtResponse> register(@RequestBody UserReadDto userReadDto, HttpServletRequest request) {
        TokenPair registerTokens = authService.register(userReadDto, request.getRemoteAddr());
        JwtResponse token = new JwtResponse(registerTokens.getAccessToken(), registerTokens.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping(LOGIN_ENDPOINT)
    public ResponseEntity<JwtResponse> login(@RequestBody UserReadDto user, HttpServletRequest request) {
        TokenPair loginTokens = authService.login(user, request.getRemoteAddr());
        JwtResponse token = new JwtResponse(loginTokens.getAccessToken(), loginTokens.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping(REFRESH_ENDPOINT)
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenDto refreshToken, HttpServletRequest request) {
        TokenPair refreshTokenPair = authService.refresh(refreshToken.refreshToken(), request.getRemoteAddr());
        JwtResponse token = new JwtResponse(refreshTokenPair.getAccessToken(), refreshTokenPair.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
