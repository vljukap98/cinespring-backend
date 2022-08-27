package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.dto.WatchedReq;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.model.Watched;
import hr.ljakovic.cinespring.model.WatchedId;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.WatchedRepo;
import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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

    public List<MovieDb> getWatchedMovies(String username) {
        final List<MovieDb> watchedMovies = new ArrayList<>();
        final AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new CineSpringException("User not found"));
        final List<Watched> watched = appUser.getWatchedList();

        watched.forEach(m -> {
            watchedMovies.add(tmdbApi.getMovies().getMovie(m.getWatchedId().getMovieId().intValue(), TmdbApiUtils.LANG));
        });

        return watchedMovies;
    }

    @Transactional
    public Watched markMovieAsWatched(WatchedReq watchedReq) {
        MovieDb movie = tmdbApi.getMovies().getMovie(watchedReq.getMovieId().intValue(), TmdbApiUtils.LANG);
        AppUser appUser = appUserRepo.findByUsername(watchedReq.getUsername())
                .orElseThrow(() -> new CineSpringException("User not found"));

        WatchedId watchedId = new WatchedId(appUser.getId(), (long) movie.getId());
        Watched newWatched = new Watched(
                watchedId,
                appUser,
                watchedReq.getStars()
        );

        if(!appUser.getWatchedList().contains(newWatched)) {
            appUser.getWatchedList().add(newWatched);

            watchedRepo.save(newWatched);
            appUserRepo.save(appUser);

            return newWatched;
        } else {
            throw new CineSpringException("Movie already marked as watched");
        }
    }

    @Transactional
    public void unmarkMovieAsWatched(WatchedReq watchedReq) {
        MovieDb movie = tmdbApi.getMovies().getMovie(watchedReq.getMovieId().intValue(), TmdbApiUtils.LANG);
        AppUser appUser = appUserRepo.findByUsername(watchedReq.getUsername())
                .orElseThrow(() -> new CineSpringException("User not found"));

        WatchedId watchedId = new WatchedId(appUser.getId(), (long) movie.getId());
        Watched watched = watchedRepo.getById(watchedId);

        if(appUser.getWatchedList().contains(watched)) {
            appUser.getWatchedList().remove(watched);

            appUserRepo.save(appUser);
            watchedRepo.delete(watched);
        } else {
            throw new CineSpringException("Movie not marked as watched");
        }
    }
}
