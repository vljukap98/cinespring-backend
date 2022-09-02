package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.ToWatchReq;
import hr.ljakovic.cinespring.model.ToWatch;
import hr.ljakovic.cinespring.service.WatchlistService;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/watchlist")
public class WatchlistController {

    @Autowired
    WatchlistService watchlistService;

    @GetMapping("/{username}")
    public ResponseEntity<List<MovieDb>> getWatchlistByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(watchlistService.getWatchlistMovies(username));
    }

    @GetMapping("/ids/{username}")
    public ResponseEntity<List<Long>> getWatchlistedIdsByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(watchlistService.getUserWatchlistedMovieIds(username));
    }

    @PostMapping("/add")
    public ResponseEntity<ToWatch> addMovieToWatchlist(@RequestBody ToWatchReq req) {
        return ResponseEntity
                .created(URI.create("/watchlist/add"))
                .body(watchlistService.addMovieToWatchlist(req));
    }

    @PostMapping("/remove")
    public void removeMovieFromWatchlist(@RequestBody ToWatchReq req) {
        watchlistService.removeMovieFromWatchlist(req);
    }
}
