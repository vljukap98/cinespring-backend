package hr.ljakovic.cinespring.repo;

import hr.ljakovic.cinespring.model.Watched;
import hr.ljakovic.cinespring.model.WatchedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchedRepo extends JpaRepository<Watched, WatchedId> {
}
