package hr.ljakovic.cinespring.repo;

import hr.ljakovic.cinespring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepo extends JpaRepository<Role, UUID> {
    Optional<Role> findByAuthority(String admin);
}
