package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.dto.ToWatchReq;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.model.ToWatch;
import hr.ljakovic.cinespring.model.ToWatchId;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.repo.ToWatchRepo;
import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class WatchlistService {

    private final TmdbApi tmdbApi;
    private final AppUserRepo appUserRepo;
    private final ToWatchRepo toWatchRepo;

    public WatchlistService(AppUserRepo appUserRepo, ToWatchRepo toWatchRepo) {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
        this.appUserRepo = appUserRepo;
        this.toWatchRepo = toWatchRepo;
    }

    public List<MovieDb> getWatchlistMovies(String username) {
        final List<MovieDb> watchlistMovies = new ArrayList<>();
        final AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new CineSpringException("User not found"));
        final List<ToWatch> watchlist = appUser.getToWatchList();

        watchlist.forEach(m -> {
            watchlistMovies.add(tmdbApi.getMovies().getMovie(m.getToWatchId().getMovieId().intValue(), TmdbApiUtils.LANG));
        });

        return watchlistMovies;
    }

    @Transactional
    public ToWatch addMovieToWatchlist(ToWatchReq toWatchReq) {
        MovieDb movie = tmdbApi.getMovies().getMovie(toWatchReq.getMovieId().intValue(), TmdbApiUtils.LANG);
        AppUser appUser = appUserRepo.findByUsername(toWatchReq.getUsername())
                .orElseThrow(() -> new CineSpringException("User not found"));

        ToWatchId toWatchId = new ToWatchId(appUser.getId(), (long) movie.getId());
        ToWatch toWatch = new ToWatch(
                toWatchId,
                appUser
        );

        if(!appUser.getToWatchList().contains(toWatch)) {
            appUser.getToWatchList().add(toWatch);

            toWatchRepo.save(toWatch);
            appUserRepo.save(appUser);

            return toWatch;
        } else {
            throw new CineSpringException("Movie already added to watchlist");
        }
    }

    @Transactional
    public void removeMovieFromWatchlist(ToWatchReq toWatchReq) {
        MovieDb movie = tmdbApi.getMovies().getMovie(toWatchReq.getMovieId().intValue(), TmdbApiUtils.LANG);
        AppUser appUser = appUserRepo.findByUsername(toWatchReq.getUsername())
                .orElseThrow(() -> new CineSpringException("User not found"));

        ToWatchId toWatchId = new ToWatchId(appUser.getId(), (long) movie.getId());
        ToWatch toWatch = toWatchRepo.getById(toWatchId);

        if(appUser.getToWatchList().contains(toWatch)) {
            appUser.getToWatchList().remove(toWatch);

            toWatchRepo.save(toWatch);
            appUserRepo.save(appUser);
        } else {
            throw new CineSpringException("Movie not added yet");
        }
    }
}
