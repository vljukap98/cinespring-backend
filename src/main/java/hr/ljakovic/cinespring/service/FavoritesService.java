package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.dto.FavoriteReq;
import hr.ljakovic.cinespring.exception.CineSpringException;
import hr.ljakovic.cinespring.model.AppUser;
import hr.ljakovic.cinespring.model.Favorite;
import hr.ljakovic.cinespring.model.FavoriteId;
import hr.ljakovic.cinespring.repo.FavoritesRepo;
import hr.ljakovic.cinespring.repo.AppUserRepo;
import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class FavoritesService {

    private final TmdbApi tmdbApi;
    private final AppUserRepo appUserRepo;
    private final FavoritesRepo favoritesRepo;

    public FavoritesService(AppUserRepo appUserRepo, FavoritesRepo favoritesRepo) {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
        this.appUserRepo = appUserRepo;
        this.favoritesRepo = favoritesRepo;
    }

    public List<MovieDb> getUserFavoriteMovies(String username) {
        final List<MovieDb> favoriteMovies = new ArrayList<>();
        final AppUser appUser =  appUserRepo.findByUsername(username).
                orElseThrow(() -> new CineSpringException("User not found"));
        final List<Favorite> favorites = appUser.getFavorites();

        favorites.forEach(f ->
                favoriteMovies.add(tmdbApi.getMovies().getMovie(f.getId().getMovieId().intValue(), TmdbApiUtils.LANG)));

        Collections.reverse(favoriteMovies);

        return favoriteMovies;
    }

    @Transactional
    public Favorite addMovieToFavorites(FavoriteReq favoriteReq) {
        MovieDb movie = tmdbApi.getMovies().getMovie(favoriteReq.getMovieId().intValue(), TmdbApiUtils.LANG);
        AppUser appUser = appUserRepo.findByUsername(favoriteReq.getUsername())
                .orElseThrow(() -> new CineSpringException("User not found"));


        FavoriteId favId = new FavoriteId(appUser.getId(), (long) movie.getId());
        Favorite favorite = new Favorite(
                favId,
                appUser
        );

        if (!appUser.getFavorites().contains(favorite)) {
            appUser.getFavorites().add(favorite);

            favoritesRepo.save(favorite);
            appUserRepo.save(appUser);

            return favorite;
        } else {
            throw new CineSpringException("Movie already marked as favorite");
        }
    }

    @Transactional
    public void removeMovieFromFavorites(FavoriteReq favoriteReq) {
        MovieDb movie = tmdbApi.getMovies().getMovie(favoriteReq.getMovieId().intValue(), TmdbApiUtils.LANG);
        AppUser appUser = appUserRepo.findByUsername(favoriteReq.getUsername())
                .orElseThrow(() -> new CineSpringException("User not found"));

        FavoriteId favId = new FavoriteId(appUser.getId(), (long) movie.getId());
        Favorite favorite = favoritesRepo.getById(favId);

        if(appUser.getFavorites().contains(favorite)) {
            appUser.getFavorites().remove(favorite);

            appUserRepo.save(appUser);
            favoritesRepo.delete(favorite);
        } else {
            throw new CineSpringException("Movie is not marked as favorite");
        }
    }

    public List<Long> getUserFavoriteMovieIds(String username) {
        AppUser appUser = appUserRepo.findByUsername(username)
                .orElseThrow(() -> new CineSpringException("User not found"));

        List<Long> movieIds = new ArrayList<>();

        appUser.getFavorites().forEach(f -> {
            movieIds.add(f.getId().getMovieId());
        });

        return movieIds;
    }
}
