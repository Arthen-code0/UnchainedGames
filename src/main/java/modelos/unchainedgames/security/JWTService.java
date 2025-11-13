package modelos.unchainedgames.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import modelos.unchainedgames.models.Profile;
import modelos.unchainedgames.models.Usuario;
import modelos.unchainedgames.services.ProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class JWTService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService; // CORREGIDO: usar UserDetailsService

    @Autowired
    private ProfileServices perfilService;

    public String generateToken(Usuario usuario){
        TokenDataDTO tokenDataDTO = TokenDataDTO
                .builder()
                .username(usuario.getUsername())
                .rol(usuario.getRol().name())
                .fecha_creacion(System.currentTimeMillis())
                .fecha_expiracion(System.currentTimeMillis() + 1000 * 60 * 60 * 3)
                .build();

        return Jwts
                .builder()
                .claim("tokenDataDTO", tokenDataDTO)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Profile extraerPerfilToken(String token){
        // CORREGIDO: Verificar si el token tiene "Bearer "
        String tokenSinCabecera = token.startsWith("Bearer ") ? token.substring(7) : token;
        TokenDataDTO tokenDataDTO = extractTokenData(tokenSinCabecera);
        Usuario usuarioLogueado = (Usuario) userDetailsService.loadUserByUsername(tokenDataDTO.getUsername());
        return perfilService.buscarPorUsuario(usuarioLogueado);
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
        Map<String, Object> mapa =  (LinkedHashMap<String,Object>) claims.get("tokenDataDTO");
        return TokenDataDTO.builder()
                .username((String) mapa.get("username"))
                .fecha_creacion((Long) mapa.get("fecha_creacion"))
                .fecha_expiracion((Long) mapa.get("fecha_expiracion"))
                .rol((String) mapa.get("rol"))
                .build();
    }

    public boolean isExpired(String token){
        return new Date(extractTokenData(token).getFecha_expiracion()).before(new Date());
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}