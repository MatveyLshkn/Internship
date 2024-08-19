package lma.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lma.constants.CommonConstants;
import lma.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import static javax.crypto.Cipher.SECRET_KEY;
import static lma.constants.CommonConstants.INVALID_SIGNATURE_MESSAGE;
import static lma.constants.CommonConstants.IP_CLAIM_NAME;
import static lma.constants.CommonConstants.MALFORMED_JWT_MESSAGE;
import static lma.constants.CommonConstants.ROLES_CLAIM_NAME;
import static lma.constants.CommonConstants.SECRET;
import static lma.constants.CommonConstants.TOKEN_EXPIRED_MESSAGE;
import static lma.constants.CommonConstants.UNSUPPORTED_JWT_MESSAGE;

@Component
@RequiredArgsConstructor
public class JwtProvider{

    public String generateAccessToken(User user, String ip) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .claim(ROLES_CLAIM_NAME, "ROLES"/*user.getRoles()*/)
                .claim(IP_CLAIM_NAME, ip)
                .compact();
    }

    public String generateRefreshToken(User user, String ip) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(refreshExpiration)
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .claim(IP_CLAIM_NAME, ip)
                .compact();
    }

    public boolean validateToken(String token, SecretKey secret, String ip) {
        System.out.println(token);
        System.out.println(SECRET);
        try {
            Jwts.parser()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String jwtIp = extractIpAddress(token);
            return jwtIp != null && jwtIp.equals(ip); //&& getClaims(token, secret).getExpiration().before(Date.from(Instant.now()));
        } catch (ExpiredJwtException expEx) {
            System.err.println(TOKEN_EXPIRED_MESSAGE);
        } catch (UnsupportedJwtException unsEx) {
            System.err.println(UNSUPPORTED_JWT_MESSAGE);
        } catch (MalformedJwtException mjEx) {
            System.err.println(MALFORMED_JWT_MESSAGE);
            System.err.println(mjEx.getMessage());
        } catch (SignatureException sEx) {
            System.err.println(INVALID_SIGNATURE_MESSAGE);
        } catch (Exception e) {
            System.err.println(INVALID_SIGNATURE_MESSAGE);
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Claims getClaims(String token, SecretKey secret) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return getClaims(token, getKey()).getSubject();
    }

    public String extractIpAddress(String token) {
        return getClaims(token, getKey()).get(IP_CLAIM_NAME, String.class);
    }

    public SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
        //return new SecretKeySpec(, SignatureAlgorithm.HS256.getJcaName());
    }
}
