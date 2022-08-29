package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.people.PersonCast;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CastService {

    private final TmdbApi tmdbApi;

    public CastService() {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
    }

    public List<PersonCast> getCastByMovieId(Long id) {
        return tmdbApi.getMovies().getMovie(id.intValue(), TmdbApiUtils.LANG).getCast();
    }
}
