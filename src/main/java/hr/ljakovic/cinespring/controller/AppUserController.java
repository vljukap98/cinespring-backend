package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.RegisterReq;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class AppUserController {


    private final RegisterService registerService;

    @PostMapping("/add")
    public ResponseEntity<AppUser> addUser(@RequestBody RegisterReq req) {
        AppUser newAppUser = registerService.registerNewUser(req);

        if(newAppUser == null) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(newAppUser);
    }
}
