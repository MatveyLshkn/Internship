package lma.service;

import io.jsonwebtoken.Claims;
import lma.dto.UserReadDto;
import lma.entity.Role;
import lma.entity.TokenPair;
import lma.entity.User;
import lma.provider.JwtProvider;
import lma.repository.TokenPairRepository;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final TokenPairRepository tokenPairRepository;
    private final JwtProvider jwtProvider;

    public TokenPair register(UserReadDto userDto, String ip) {
        User user = userRepository.getByUsername(userDto.username());
        if (user != null) {
            throw new RuntimeException("Username is already in use");
        }

        String accessToken = jwtProvider.generateAccessToken(user, ip);
        String refreshToken = jwtProvider.generateRefreshToken(user, ip);

        TokenPair tokenPair = TokenPair.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        User build = User.builder()
                .username(userDto.username())
                .password(userDto.password())
                .roles(List.of(new Role()))
                .build();
        build.addToken(tokenPair);
        userRepository.save(build);

        tokenPairRepository.save(tokenPair);

        return tokenPair;
    }

    public TokenPair login(@NonNull UserReadDto userDto, String ip) {
        User user = userRepository.getByUsername(userDto.username());
        if (user == null) {
            throw new RuntimeException("Пользователь не найден");
        }

        if (user.getPassword().equals(userDto.password())) {
            String accessToken = jwtProvider.generateAccessToken(user, ip);
            String refreshToken = jwtProvider.generateRefreshToken(user, ip);
            TokenPair tokenPair = TokenPair.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();

            tokenPairRepository.save(tokenPair);

            return tokenPair;
        } else {
            throw new RuntimeException("Неверный пароль");
        }
    }

    public TokenPair refresh(@NonNull String refreshToken, String ip) {
        if (jwtProvider.validateToken(refreshToken, "secret", ip)) {
            String username = jwtProvider.extractUsername(refreshToken);
            User user = userRepository.getByUsername(username);
            if (user == null) {
                throw new RuntimeException();
            }

            boolean exists = tokenPairRepository.existsTokenPairByRefreshTokenAndUser_Id(refreshToken, user.getId());
            if (exists) {
                String accessToken = jwtProvider.generateAccessToken(user, ip);
                tokenPairRepository.updateTokenPairByRefreshToken(accessToken, refreshToken, refreshToken);
            } else {
                throw new RuntimeException();
            }
        } else {
            tokenPairRepository.deleteByRefreshToken(refreshToken);
        }
        return null;
        //throw new AuthException("Невалидный JWT токен");
    }
}
