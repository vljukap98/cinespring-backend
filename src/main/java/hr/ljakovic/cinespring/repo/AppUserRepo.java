package hr.ljakovic.cinespring.repo;

import hr.ljakovic.cinespring.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, UUID> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(au) > 0 THEN true ELSE false END FROM AppUser au JOIN au.roles rau WHERE rau.authority='ADMIN'")
    boolean findSuperAdmin();
}
