package lma.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lma.entity.TokenPair;
import lma.entity.User;
import lma.exception.InvalidTokenException;
import lombok.experimental.UtilityClass;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static lma.constants.CommonConstants.DEFAULT_ACCESS_TOKEN_TTL;
import static lma.constants.CommonConstants.DEFAULT_REFRESH_TOKEN_TTL;
import static lma.constants.CommonConstants.IP_CLAIM_NAME;
import static lma.constants.CommonConstants.ROLES_CLAIM_NAME;
import static lma.constants.CommonConstants.SECRET;

@UtilityClass
public class JwtUtil {

    public static TokenPair generateTokenPair(User user, String ip) {
        return TokenPair.builder()
                .accessToken(generateAccessToken(user, ip))
                .refreshToken(generateRefreshToken(user, ip))
                .build();
    }

    public static String generateAccessToken(User user, String ip) {

        Date accessTokenExpiration = Date.from(
                Instant.now().plus(DEFAULT_ACCESS_TOKEN_TTL)
        );

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(accessTokenExpiration)
                .signWith(SignatureAlgorithm.HS256, getKey())
                .claim(ROLES_CLAIM_NAME, user.getRoles())
                .claim(IP_CLAIM_NAME, ip)
                .compact();
    }

    public static String generateRefreshToken(User user, String ip) {

        Date refreshTokenExpiration = Date.from(
                Instant.now().plus(DEFAULT_REFRESH_TOKEN_TTL)
        );

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(refreshTokenExpiration)
                .signWith(SignatureAlgorithm.HS256, getKey())
                .claim(IP_CLAIM_NAME, ip)
                .compact();
    }

    public static boolean isTokenValid(String token, String ip) throws InvalidTokenException {
        try {
            Jwts.parser()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String jwtIp = extractIpAddress(token);
            return jwtIp != null && jwtIp.equals(ip);

        } catch (Exception e) {
            return false;
        }
    }

    public static Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public static String extractIpAddress(String token) {
        return getClaims(token).get(IP_CLAIM_NAME, String.class);
    }

    private static SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
