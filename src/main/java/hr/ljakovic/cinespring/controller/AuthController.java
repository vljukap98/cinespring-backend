package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.AuthReq;
import hr.ljakovic.cinespring.dto.AuthRes;
import hr.ljakovic.cinespring.dto.RegisterReq;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.service.AuthService;
import hr.ljakovic.cinespring.service.RegTokenService;
import hr.ljakovic.cinespring.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    RegisterService registerService;

    @Autowired
    RegTokenService regTokenService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthRes> authenticate(@RequestBody AuthReq authReq) throws CineSpringException{
        return ResponseEntity.ok(authService.authenticateUser(authReq));
    }

    @PostMapping("/register")
    public ResponseEntity<AppUser> addUser(@RequestBody RegisterReq req) {
        AppUser newAppUser = registerService.registerNewUser(req);

        if(newAppUser == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(newAppUser);
    }

    @GetMapping("/verify")
    public void verifyNewAccount(@RequestParam("token") UUID token) {
        regTokenService.confirmToken(token);
    }
}
