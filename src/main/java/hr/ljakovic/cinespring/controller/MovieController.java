package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.service.GenreService;
import hr.ljakovic.cinespring.service.MovieService;
import info.movito.themoviedbapi.model.Genre;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @Autowired

    GenreService genreService;

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

    @GetMapping("/discover")
    public ResponseEntity<MovieResultsPage> discoverMovies() {
        return ResponseEntity.ok().body(movieService.discoverMovies());
    }

    @GetMapping("/random")
    public ResponseEntity<MovieDb> getRandomMovie() {
        return ResponseEntity.ok().body(movieService.getRandomMovie());
    }


    @GetMapping("/genre/{id}")
    public ResponseEntity<List<Genre>> getCrewByMovieId(@PathVariable Long id) {
        return ResponseEntity.ok().body(genreService.getGenresByMovieId(id));
    }
}
