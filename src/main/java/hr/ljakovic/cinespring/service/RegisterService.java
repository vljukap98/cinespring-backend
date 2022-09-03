package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.dto.RegisterReq;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.model.RegToken;
import hr.ljakovic.cinespring.model.Role;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class RegisterService {

    @Autowired
    AppUserRepo appUserRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    RegTokenService regTokenService;

    @Autowired
    EmailService emailService;

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
                .isActivated(false)
                .id(UUID.randomUUID())
                .email(registerReq.getEmail())
                .username(registerReq.getUsername())
                .password(passwordEncoder.encode(registerReq.getPassword()))
                .roles(List.of(userRole))
                .build();

        appUserRepo.save(newAppUser);

        userRole.getAppUsers().add(newAppUser);
        roleRepo.save(userRole);

        RegToken regToken = RegToken
                .builder()
                .id(UUID.randomUUID())
                .appUser(newAppUser)
                .build();

        regTokenService.saveToken(regToken);

        String confirmationLink = "http://localhost:8080/auth/verify?token=" + regToken.getId().toString();

        emailService.send(
                registerReq.getEmail(),
                "Verify account email",
                "Please verify account by visiting: " + confirmationLink
        );

        return newAppUser;
    }
}
