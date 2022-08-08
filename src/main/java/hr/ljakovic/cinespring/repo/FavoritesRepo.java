package hr.ljakovic.cinespring.repo;

import hr.ljakovic.cinespring.model.Favorite;
import hr.ljakovic.cinespring.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritesRepo extends JpaRepository<Favorite, FavoriteId> {

}
