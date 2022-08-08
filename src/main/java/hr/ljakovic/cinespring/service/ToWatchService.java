package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.ToWatchRepo;
import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import org.springframework.stereotype.Service;

@Service
public class ToWatchService {

    private final TmdbApi tmdbApi;
    private final AppUserRepo appUserRepo;
    private final ToWatchRepo toWatchRepo;

    public ToWatchService(AppUserRepo appUserRepo, ToWatchRepo toWatchRepo) {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
        this.appUserRepo = appUserRepo;
        this.toWatchRepo = toWatchRepo;
    }
}
