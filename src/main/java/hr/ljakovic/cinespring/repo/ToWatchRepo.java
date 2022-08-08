package hr.ljakovic.cinespring.repo;

import hr.ljakovic.cinespring.model.ToWatch;
import hr.ljakovic.cinespring.model.ToWatchId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToWatchRepo extends JpaRepository<ToWatch, ToWatchId> {
}
