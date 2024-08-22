package lma.service;


import lma.dto.JwtResponse;
import lma.dto.UserLoginDto;
import lma.dto.UserRegisterDto;
import lma.entity.TokenPair;
import lma.entity.User;
import lma.exception.InvalidTokenException;
import lma.exception.UserAlreadyExistsException;
import lma.exception.UserNotFoundException;
import lma.exception.WrongPasswordException;
import lma.mapper.UserMapper;
import lma.repository.RoleRepository;
import lma.repository.TokenPairRepository;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static lma.constants.ExceptionConstants.INVALID_TOKEN_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.USER_ALREADY_EXISTS_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.USER_NOT_FOUND_BY_USERNAME_EXCEPTION_MESSAGE;
import static lma.constants.ExceptionConstants.WRONG_PASSWORD_EXCEPTION_MESSAGE;
import static lma.util.JwtUtil.extractUsername;
import static lma.util.JwtUtil.generateAccessToken;
import static lma.util.JwtUtil.generateTokenPair;
import static lma.util.JwtUtil.isTokenValid;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final TokenPairRepository tokenPairRepository;

    public JwtResponse register(UserRegisterDto userDto, String ip) {

        User user = userRepository.getByUsername(userDto.username());
        if (user != null) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS_EXCEPTION_MESSAGE.formatted(userDto.username()));
        }

        user = userMapper.mapFromRegisterDto(userDto);

        TokenPair tokenPair = generateTokenPair(user, ip);

        user.addToken(tokenPair);
        userRepository.save(user);

        return new JwtResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
    }

    public JwtResponse login(UserLoginDto userDto, String ip) {

        User user = userRepository.getByUsername(userDto.username());
        if (user == null) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_USERNAME_EXCEPTION_MESSAGE.formatted(userDto.username()));
        }

        if (passwordEncoder.matches(userDto.password(), user.getPassword())) {

            TokenPair tokenPair = generateTokenPair(user, ip);
            tokenPair.setUser(user);

            tokenPairRepository.save(tokenPair);

            return new JwtResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
        } else {
            throw new WrongPasswordException(WRONG_PASSWORD_EXCEPTION_MESSAGE);
        }
    }

    public JwtResponse refresh(String refreshToken, String ip) {

        if (isTokenValid(refreshToken, ip)) {

            String username = extractUsername(refreshToken);
            User user = userRepository.getByUsername(username);
            if (user == null) {
                throw new UserNotFoundException(USER_NOT_FOUND_BY_USERNAME_EXCEPTION_MESSAGE.formatted(username));
            }

            TokenPair tokenPair = tokenPairRepository.findByRefreshTokenAndUser_Id(refreshToken, user.getId());
            if (tokenPair != null) {
                String accessToken = generateAccessToken(user, ip);
                tokenPair.setAccessToken(accessToken);

                tokenPairRepository.save(tokenPair);

                return new JwtResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken());
            } else {
                throw new InvalidTokenException(INVALID_TOKEN_EXCEPTION_MESSAGE);
            }

        } else {
            tokenPairRepository.deleteByRefreshToken(refreshToken);
            throw new InvalidTokenException(INVALID_TOKEN_EXCEPTION_MESSAGE);
        }
    }
}