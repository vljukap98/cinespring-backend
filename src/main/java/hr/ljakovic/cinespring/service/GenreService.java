package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.Credits;
import info.movito.themoviedbapi.model.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final TmdbApi tmdbApi;

    public GenreService() {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
    }

    public List<Genre> getGenresByMovieId(Long id) {
        return tmdbApi.getMovies().getMovie(id.intValue(), TmdbApiUtils.LANG).getGenres();
    }
}
