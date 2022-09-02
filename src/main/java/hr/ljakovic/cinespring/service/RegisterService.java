package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.dto.RegisterReq;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.model.Role;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.RoleRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RegisterService {

    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public AppUser registerNewUser(RegisterReq registerReq) {
        final boolean usernameExists = appUserRepo.findByUsername(registerReq.getUsername()).isPresent();
        final boolean emailExists = appUserRepo.findByEmail(registerReq.getEmail()).isPresent();

        if(usernameExists) {
            throw new CineSpringException("Entered username already exists");
        }

        if(emailExists) {
            throw new CineSpringException("Entered email is already in use");
        }

        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow();

        AppUser newAppUser = AppUser.builder()
                .isActivated(true) //set this as false, then when verifying email set it as true
                .id(UUID.randomUUID())
                .email(registerReq.getEmail())
                .username(registerReq.getUsername())
                .password(passwordEncoder.encode(registerReq.getPassword()))
                .roles(List.of(userRole))
                .build();

        appUserRepo.save(newAppUser);

        userRole.getAppUsers().add(newAppUser);
        roleRepo.save(userRole);

        //TODO: send email to the newly created user

        return newAppUser;
    }

    public void verifyNewUserEmail(UUID token) {

    }
}
