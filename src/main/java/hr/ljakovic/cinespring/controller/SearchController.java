package hr.ljakovic.cinespring.controller;

import hr.ljakovic.cinespring.service.SearchService;
import info.movito.themoviedbapi.TmdbSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/{search}/{page}")
    public ResponseEntity<TmdbSearch.MultiListResultsPage> getSearchResults(@PathVariable String search, @PathVariable Long page) {
        return ResponseEntity.ok().body(searchService.searchByInputString(search, page));
    }
}
