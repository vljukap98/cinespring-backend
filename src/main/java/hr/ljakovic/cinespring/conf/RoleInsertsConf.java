package hr.ljakovic.cinespring.conf;

import hr.ljakovic.cinespring.model.Role;
import hr.ljakovic.cinespring.repo.RoleRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.UUID;

@Configuration
public class RoleInsertsConf {

    @Bean
    public CommandLineRunner clr(RoleRepo roleRepo) {
        return args -> {
            insertUserRole(roleRepo);
            insertAdminRole(roleRepo);
        };
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
