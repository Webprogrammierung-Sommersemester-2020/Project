package com.pizzashop.security.services;

import com.pizzashop.data.models.User;
import com.pizzashop.data.repositories.implementations.UserRepository;
import com.pizzashop.data.services.IUserDataService;
import com.pizzashop.data.services.implementations.UserDataService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.StringTokenizer;

public class TokenService {

    private static HttpSession session = null;
    private static IUserDataService userDataService = new UserDataService(new UserRepository());

    private static String getCredentialsStringFrom(String authorization) {

        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);

        return new String(credDecoded, StandardCharsets.UTF_8);
    }

    private static boolean isBasic(String authorization) {
        return authorization != null && authorization.toLowerCase().startsWith("basic");
    }

    public static String create(@Context HttpServletRequest request) {
        User user;
        StringTokenizer tokenizer = null;

        final String authorization = request.getHeader("Authorization");

        if (isBasic(authorization)) {

            String credentials = getCredentialsStringFrom(authorization);
            tokenizer = new StringTokenizer(credentials, ":");

            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();

            if (username != null) {
                user = userDataService.getUserByUserName(username);
                if (password.equals(user.getPassword())) {
                    Key signingKey = new SecretKeySpec(
                            DatatypeConverter.parseBase64Binary(password), SignatureAlgorithm.HS512.getJcaName());

                    session = request.getSession();
                    session.setAttribute("username", user.getUserName());

                    return Jwts.builder()
                            .setSubject(username)
                            .claim("role", user.getRole())
                            .signWith(SignatureAlgorithm.HS512, signingKey)
                            .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.MINUTES)))
                            .compact();
                }
            }
        }
        return null;
    }

    public static String validateTokenAndReturnRole(String token) {
        User user;
        String role = "";
        if ((session != null)) {
            if (session.getAttribute("username") != null) {
                String username = (String) session.getAttribute("username");
                if (!username.isBlank()) {
                    user = userDataService.getUserByUserName(username);
                    Key signingKey = new SecretKeySpec(
                            DatatypeConverter.parseBase64Binary(user.getPassword()), SignatureAlgorithm.HS512.getJcaName());
                    Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();

                    role = claims.get("role").toString();
                    Jwts.parser()
                            .requireSubject(claims.getSubject())
                            .setSigningKey(signingKey)
                            .parseClaimsJws(token);
                }
            }

        }
        return role;
    }

}
