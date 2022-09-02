package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @PostMapping("/{username}")
    public ResponseEntity<AppUser> getUserData(@PathVariable String username) {
        return ResponseEntity.ok().body(appUserService.getAppUserData(username));
    }
}
