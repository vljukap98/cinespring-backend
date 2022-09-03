package hr.ljakovic.cinespring.conf;

import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.model.Role;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration
public class DbInsertsConf {

    @Bean
    @PostConstruct
    @EventListener(ApplicationReadyEvent.class)
    public CommandLineRunner clr(RoleRepo roleRepo, AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            insertUserRole(roleRepo);
            insertAdminRole(roleRepo);
            insertSuperAdmin(appUserRepo, roleRepo, passwordEncoder);
        };
    }

    private void insertSuperAdmin(AppUserRepo appUserRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        boolean superAdminExists = appUserRepo.findSuperAdmin();

        if(!superAdminExists) {
            Role adminRole = roleRepo.findByAuthority("ADMIN")
                    .orElseThrow(() -> new CineSpringException("Role not found"));

            AppUser superAppUser = AppUser.builder()
                            .isActivated(true)
                            .id(UUID.randomUUID())
                            .email("master@mail.com")
                            .username("master")
                            .password(passwordEncoder.encode("master"))
                            .roles(List.of(adminRole))
                            .build();

            adminRole.getAppUsers().add(superAppUser);

            appUserRepo.save(superAppUser);
            roleRepo.save(adminRole);
        }

    }

    private void insertAdminRole(RoleRepo roleRepo) {
        boolean adminExists = roleRepo
                .findByAuthority("ADMIN")
                .isPresent();

        if(!adminExists) {
            roleRepo.save(
                    new Role(
                            UUID.randomUUID(),
                            "ADMIN",
                            new ArrayList<>()
                    )
            );
        }
    }

    private void insertUserRole(RoleRepo roleRepo) {
        boolean userExists = roleRepo
                .findByAuthority("USER")
                .isPresent();

        if(!userExists) {
            roleRepo.save(
                    new Role(
                            UUID.randomUUID(),
                            "USER",
                            new ArrayList<>()
                    )
            );
        }
    }
}
