package ro.utm.jc.identity;

import ro.utm.jc.model.constants.Role;
import ro.utm.jc.model.entities.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class TokenUtil {

    //private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10 days Validity
    private static final long VALIDITY_TIME_MS =  2 * 60 * 60 * 1000; // 2 hours  validity
    private static final String AUTH_HEADER_NAME = "Authorization";

    private String secret="mrin";

    public Optional<Authentication> verifyToken(HttpServletRequest request) {
        final String token = request.getHeader(AUTH_HEADER_NAME);

        if (token != null && !token.isEmpty()){
            final TokenUser user = parseUserFromToken(token.replace("Bearer","").trim());
            if (user != null) {
                return  Optional.of(new UserAuthentication(user));
            }
        }
        return Optional.empty();

    }

    //Get Users Info from the Token
    public TokenUser parseUserFromToken(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Users user = new Users();
        user.setUserId( (String)claims.get("userId"));
        user.setRole(Role.valueOf((String)claims.get("role")));
        if (user.getUserId() != null && user.getRole() != null) {
            return new TokenUser(user);
        } else {
            return null;
        }
    }

    public String createTokenForUser(TokenUser tokenUser) {
        return createTokenForUser(tokenUser.getUser());
    }

    public String createTokenForUser(Users user) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
                .setSubject(user.getFullName())
                .claim("userId", user.getUserId())
                .claim("role", user.getRole().toString())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

}
