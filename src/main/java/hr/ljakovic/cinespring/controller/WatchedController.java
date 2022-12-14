package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.WatchedReq;
import hr.ljakovic.cinespring.model.Watched;
import hr.ljakovic.cinespring.service.WatchedService;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/watched")
public class WatchedController {

    @Autowired
    WatchedService watchedService;

    @GetMapping("/{username}")
    public ResponseEntity<List<MovieDb>> getWatchedByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(watchedService.getWatchedMovies(username));
    }

    @GetMapping("/ids/{username}")
    public ResponseEntity<List<Long>> getWatchedIdsByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(watchedService.getUserWatchedMovieIds(username));
    }

    @PostMapping("/add")
    public ResponseEntity<Watched> markMovieAsWatched(@RequestBody WatchedReq watchedReq) {
        return ResponseEntity
                .created(URI.create("/watched/add"))
                .body(watchedService.markMovieAsWatched(watchedReq));
    }

    @PostMapping("/remove")
    public void unmarkMovieAsWatched(@RequestBody WatchedReq watchedReq) {
        watchedService.unmarkMovieAsWatched(watchedReq);
    }

    @PutMapping("/modify-stars")
    public ResponseEntity<Watched> modifyWatchedMovieStars(@RequestBody WatchedReq watchedReq) {
        return ResponseEntity.ok().body(watchedService.modifyWatchedMovieStars(watchedReq));
    }

    @GetMapping("/stars/{movieId}/{username}")
    public ResponseEntity<Double> getMovieStars(@PathVariable Long movieId, @PathVariable String username) {
        return ResponseEntity.ok().body(watchedService.getMovieStars(movieId, username));
    }
}
