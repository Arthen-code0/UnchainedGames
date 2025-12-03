package modelos.unchainedgames.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import modelos.unchainedgames.listed.Rol;
import modelos.unchainedgames.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    private UserDetailsService userDetailsService;

    public String generateToken(Usuario usuario){
        TokenDataDTO datos = TokenDataDTO
                .builder()
                .MailUsername(usuario.getEmail())
                .rol(usuario.getRol())
                .fechaCreacion(LocalDateTime.now().toString())
                .fechaExpiracion(LocalDateTime.now().plusHours(2).toString())
                .build();

        return Jwts
                .builder()
                .claim("datos", datos)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Claims extractDatosToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();


    }

    public TokenDataDTO extractTokenData(String token){
        Claims claims = extractDatosToken(token);
        Map<String, Object> mapa = (LinkedHashMap<String,Object>) claims.get("datos");
        return TokenDataDTO.builder()
                .MailUsername((String) mapa.get("mailUsername"))
                .rol(Rol.valueOf((String) mapa.get("rol")))
                .fechaCreacion((String) mapa.get("fechaCreacion"))
                .fechaExpiracion((String) mapa.get("fechaExpiracion"))
                .build();
    }

    public boolean isExpired(String token) {
        return LocalDateTime.parse(extractTokenData(token).getFechaExpiracion()).isBefore(LocalDateTime.now());
    }


    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
