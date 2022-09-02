package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.FavoriteReq;
import hr.ljakovic.cinespring.model.Favorite;
import hr.ljakovic.cinespring.service.FavoritesService;
import info.movito.themoviedbapi.model.MovieDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    @Autowired
    FavoritesService favoritesService;

    @GetMapping("/{username}")
    public ResponseEntity<List<MovieDb>> getFavoritesByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(favoritesService.getUserFavoriteMovies(username));
    }

    @GetMapping("/ids/{username}")
    public ResponseEntity<List<Long>> getFavoriteIdsByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(favoritesService.getUserFavoriteMovieIds(username));
    }

    @PostMapping("/add")
    public ResponseEntity<Favorite> addMovieToFavorites(@RequestBody FavoriteReq req) {
        return ResponseEntity
                .created(URI.create("/favorites/add"))
                .body(favoritesService.addMovieToFavorites(req));
    }

    @PostMapping("/remove")
    public void removeMovieToFavorites(@RequestBody FavoriteReq req) {
        favoritesService.removeMovieFromFavorites(req);
    }
}
