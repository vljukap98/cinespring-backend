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
import java.util.regex.Pattern;

@Service
public class RegisterService {

    private final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$");
    private final Pattern USERNAME_PATTERN = Pattern.compile("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$");

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

        if(!checkReqPattern(registerReq)) {
            throw new CineSpringException("Entered username, email or password aren't allowed");
        }

        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow(() -> new CineSpringException("Role not found"));

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

        String confirmationLink = "https://cinespring-postgresql.herokuapp.com/auth/verify?token=" + regToken.getId().toString();

        emailService.send(
                registerReq.getEmail(),
                "Verify account email",
                "Please verify account by visiting: " + confirmationLink
        );

        return newAppUser;
    }

    private Boolean checkReqPattern(RegisterReq req) {

        return EMAIL_PATTERN.matcher(req.getEmail()).find()
                && PASSWORD_PATTERN.matcher(req.getPassword()).find()
                && USERNAME_PATTERN.matcher(req.getUsername()).find();
    }
}
