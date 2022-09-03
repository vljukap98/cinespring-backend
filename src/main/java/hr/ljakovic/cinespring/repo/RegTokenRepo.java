package hr.ljakovic.cinespring.repo;

import hr.ljakovic.cinespring.model.RegToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RegTokenRepo extends JpaRepository<RegToken, UUID> {
}
