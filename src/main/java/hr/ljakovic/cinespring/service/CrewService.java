package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.people.PersonCrew;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrewService {

    private final TmdbApi tmdbApi;

    public CrewService() {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
    }

    public List<PersonCrew> getCrewByMovieId(Long id) {
        return tmdbApi.getMovies().getMovie(id.intValue(), TmdbApiUtils.LANG).getCrew();
    }
}
