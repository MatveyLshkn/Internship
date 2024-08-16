package lma.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lma.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    public String generateAccessToken(@NonNull User user, String ip) {
        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.ES256, "secret")
                .claim("roles", user.getRoles())
                .claim("ip", ip)
                .compact();
    }

    public String generateRefreshToken(@NonNull User user, String ip) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(refreshExpiration)
                .signWith(SignatureAlgorithm.ES256, "secret")
                .claim("ip", ip)
                .compact();
    }

    public boolean validateToken(@NonNull String token, @NonNull String secret, String ip) {
        try {
            Jwts.parser()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            String jwtIp = extractIpAddress(token);
            return jwtIp != null && jwtIp.equals(ip);
        } catch (ExpiredJwtException expEx) {
            System.err.println("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            System.err.println("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            System.err.println("Malformed jwt");
        } catch (SignatureException sEx) {
            System.err.println("Invalid signature");
        } catch (Exception e) {
            System.err.println("invalid token");
        }
        return false;
    }

    public Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(Claims claims, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return getClaims(token, "secret").getSubject();
    }

    public String extractIpAddress(String token) {
        return getClaims(token, "secret").get("ip", String.class);
    }

    /*private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }*/
}
