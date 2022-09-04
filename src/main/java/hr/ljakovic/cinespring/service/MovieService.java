package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Discover;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MovieService {

    private final TmdbApi tmdbApi;

    public MovieService() {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
    }

    public MovieDb getMovie(Long id) {
        return tmdbApi.getMovies().getMovie(id.intValue(), TmdbApiUtils.LANG);
    }

    public MovieResultsPage getSimilarMovies(Long id, Long page) {
        return tmdbApi.getMovies().getSimilarMovies(id.intValue(), TmdbApiUtils.LANG, page.intValue());
    }

    public MovieResultsPage getPopularMovies(Long page) {
        return tmdbApi.getMovies().getPopularMovies(TmdbApiUtils.LANG, page.intValue());
    }

    public MovieResultsPage getTopMovies(Long page) {
        return tmdbApi.getMovies().getTopRatedMovies(TmdbApiUtils.LANG, page.intValue());
    }

    public MovieResultsPage discoverMovies() {
        return tmdbApi.getDiscover().getDiscover(new Discover());
    }

    public MovieDb getRandomMovie() {
        final Random random = new Random();

        MovieResultsPage movieResultsPage = tmdbApi
                .getMovies()
                .getTopRatedMovies(
                        TmdbApiUtils.LANG,
                        random.nextInt(50)
                );

        return movieResultsPage.getResults().get(random.nextInt(20));
    }
}
