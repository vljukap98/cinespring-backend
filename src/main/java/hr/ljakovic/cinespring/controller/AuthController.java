package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.AuthReq;
import hr.ljakovic.cinespring.dto.AuthRes;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //TODO: create endpoints for login, registration, logout, refreshing web token

    @PostMapping("/authenticate")
    public ResponseEntity<AuthRes> authenticate(@RequestBody AuthReq authReq) throws CineSpringException{
        return ResponseEntity.ok(authService.authenticateUser(authReq));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody String jwt) {
        authService.logoutUser(jwt);
    }
}
