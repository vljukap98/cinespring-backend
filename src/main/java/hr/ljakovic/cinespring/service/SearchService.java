package hr.ljakovic.cinespring.service;

import hr.ljakovic.cinespring.util.TmdbApiUtils;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final TmdbApi tmdbApi;

    public SearchService() {
        this.tmdbApi = new TmdbApi(TmdbApiUtils.API_KEY);
    }

    public TmdbSearch.MultiListResultsPage searchByInputString(String s, Long page) {
        return tmdbApi.getSearch().searchMulti(s, TmdbApiUtils.LANG, page.intValue());
    }
}
