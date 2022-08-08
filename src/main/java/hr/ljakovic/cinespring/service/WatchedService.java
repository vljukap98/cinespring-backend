package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.WatchedRepo;
import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import org.springframework.stereotype.Service;

@Service
public class WatchedService {

    private final TmdbApi tmdbApi;
    private final AppUserRepo appUserRepo;
    private final WatchedRepo watchedRepo;

    public WatchedService(AppUserRepo appUserRepo, WatchedRepo watchedRepo) {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
        this.appUserRepo = appUserRepo;
        this.watchedRepo = watchedRepo;
    }
}
