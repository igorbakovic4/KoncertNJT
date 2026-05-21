package rs.fon.koncert_app.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import rs.fon.koncert_app.entity.Korisnik;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generisiToken(Korisnik korisnik) {
        return Jwts.builder()
                .subject(korisnik.getEmail())
                .claim("id", korisnik.getId())
                .claim("ime", korisnik.getIme())
                .claim("uloga", korisnik.getUloga().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKljuc())
                .compact();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public boolean jeValidan(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKljuc())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKljuc() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}