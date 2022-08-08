package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.dto.FavoriteReq;
import hr.ljakovic.cinespring.model.Favorite;
import hr.ljakovic.cinespring.service.FavoritesService;
import info.movito.themoviedbapi.model.MovieDb;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    @GetMapping("/{id}")
    public ResponseEntity<List<MovieDb>> getFavoritesByUserid(@PathVariable UUID id) {
        return ResponseEntity.ok().body(favoritesService.getUserFavoriteMovies(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Favorite> addMovieToFavorites(@RequestBody FavoriteReq favoriteReq) {
        return ResponseEntity
                .created(URI.create("/favorites/add"))
                .body(favoritesService.addMovieToFavorites(favoriteReq));
    }

    @PostMapping("/remove")
    public void removeMovieToFavorites(@RequestBody FavoriteReq favoriteReq) {
        favoritesService.removeMovieFromFavorites(favoriteReq);
    }
}
