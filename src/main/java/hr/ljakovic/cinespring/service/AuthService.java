package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.dto.AuthReq;
import hr.ljakovic.cinespring.dto.AuthRes;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CineSpringUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthRes authenticateUser(AuthReq authReq) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authReq.getUsername(),
                            authReq.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new CineSpringException("Incorrect username or password or user not activated");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authReq.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return new AuthRes(jwt, userDetails.getUsername(), jwtUtil.extractExpiration(jwt).getTime());
    }

    public void logoutUser(String jwt) {
    }
}
