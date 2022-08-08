package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.FavoritesRepo;
import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import org.springframework.stereotype.Service;

@Service
public class WatchedService {

    private final TmdbApi tmdbApi;
    private final AppUserRepo appUserRepo;
    private final FavoritesRepo favoritesRepo;

    public WatchedService(AppUserRepo appUserRepo, FavoritesRepo favoritesRepo) {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
        this.appUserRepo = appUserRepo;
        this.favoritesRepo = favoritesRepo;
    }
}
