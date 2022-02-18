package com.mmg.moviesandtv.api.controllers;

import com.mmg.moviesandtv.api.models.movies.Movie;
import com.mmg.moviesandtv.api.models.movies.MovieList;
import com.mmg.moviesandtv.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Info: https://developers.themoviedb.org/3/movies/get-movie-details
     * @param movie_id
     * @return
     */
    @GetMapping("/{movie_id}")
    public ResponseEntity<Movie> getDetails(@PathVariable Integer movie_id) {
        return movieService.getDetails(movie_id);
    }

    /**
     * Info: https://developers.themoviedb.org/3/movies/get-popular-movies
     * @param page
     * @return
     */
    @GetMapping("/popular/{page}")
    public ResponseEntity<MovieList> getPopular(@PathVariable Integer page) {
        return movieService.getPopular(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/movies/get-top-rated-movies
     * @param page
     * @return
     */
    @GetMapping("/top_rated/{page}")
    public ResponseEntity<MovieList> getTopRated(@PathVariable Integer page) {
        return movieService.getTopRated(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/movies/get-upcoming
     * @param page
     * @return
     */
    @GetMapping("/upcoming/{page}")
    public ResponseEntity<MovieList> getUpcoming(@PathVariable Integer page) {
        return movieService.getUpcoming(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/movies/get-now-playing
     * @param page
     * @return
     */
    @GetMapping("/now_playing/{page}")
    public ResponseEntity<MovieList> getNowPlaying(@PathVariable Integer page) {
        return movieService.getNowPlaying(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/search/search-movies
     * @param searchQuery
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<MovieList> searchMovie(@RequestParam String searchQuery, @RequestParam Integer page) {
        return movieService.getSearchResults(searchQuery, page);
    }
}
