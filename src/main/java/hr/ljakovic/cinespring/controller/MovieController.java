package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.service.CastService;
import hr.ljakovic.cinespring.service.CrewService;
import hr.ljakovic.cinespring.service.MovieService;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;
    private final CastService castService;
    private final CrewService crewService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieDb> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok().body(movieService.getMovie(id));
    }

    @GetMapping("/{id}/{page}")
    public ResponseEntity<MovieResultsPage> getSimilarMoviesById(@PathVariable Long id, @PathVariable Long page) {
        return ResponseEntity.ok().body(movieService.getSimilarMovies(id, page));
    }

    @GetMapping("/popular/{page}")
    public ResponseEntity<MovieResultsPage> getPopularMovies(@PathVariable Long page) {
        return ResponseEntity.ok().body(movieService.getPopularMovies(page));
    }

    @GetMapping("/top/{page}")
    public ResponseEntity<MovieResultsPage> getTopMovies(@PathVariable Long page) {
        return ResponseEntity.ok().body(movieService.getTopMovies(page));
    }

    @GetMapping("/random")
    public ResponseEntity<MovieDb> getRandomMovie() {
        return ResponseEntity.ok().body(movieService.getRandomMovie());
    }

    @GetMapping("/cast/{id}")
    public ResponseEntity<List<PersonCast>> getCastByMovieId(@PathVariable Long id) {
        return ResponseEntity.ok().body(castService.getCastByMovieId(id));
    }

    @GetMapping("/crew/{id}")
    public ResponseEntity<List<PersonCrew>> getCrewByMovieId(@PathVariable Long id) {
        return ResponseEntity.ok().body(crewService.getCrewByMovieId(id));
    }
}
