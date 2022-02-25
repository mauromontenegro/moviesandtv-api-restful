package com.mmg.moviesandtv.api.controllers;

import com.mmg.moviesandtv.api.models.movies.Movie;
import com.mmg.moviesandtv.api.models.movies.MovieList;
import com.mmg.moviesandtv.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Provides information about Movies.
 * @Author Mauro Montenegro Guzmán (https://mauromontenegro.github.io/)
 */
@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Gets Movie details after verifying it.
     * Info: https://developers.themoviedb.org/3/movies/get-movie-details
     * @param movie_id
     * @return
     */
    @GetMapping("/{movie_id}")
    public ResponseEntity<Movie> getDetails(@PathVariable Integer movie_id) {
        return movieService.getDetails(movie_id);
    }

    /**
     * Gets a list of 'Popular' Movies.
     * Info: https://developers.themoviedb.org/3/movies/get-popular-movies
     * @param page
     * @return
     */
    @GetMapping("/popular/{page}")
    public ResponseEntity<MovieList> getPopular(@PathVariable Integer page) {
        return movieService.getPopular(page);
    }

    /**
     * Gets a list of 'Top Rated' Movies.
     * Info: https://developers.themoviedb.org/3/movies/get-top-rated-movies
     * @param page
     * @return
     */
    @GetMapping("/top_rated/{page}")
    public ResponseEntity<MovieList> getTopRated(@PathVariable Integer page) {
        return movieService.getTopRated(page);
    }

    /**
     * Gets a list of 'Upcoming' Movies.
     * Info: https://developers.themoviedb.org/3/movies/get-upcoming
     * @param page
     * @return
     */
    @GetMapping("/upcoming/{page}")
    public ResponseEntity<MovieList> getUpcoming(@PathVariable Integer page) {
        return movieService.getUpcoming(page);
    }

    /**
     * Gets a list of 'Now Playing' Movies.
     * Info: https://developers.themoviedb.org/3/movies/get-now-playing
     * @param page
     * @return
     */
    @GetMapping("/now_playing/{page}")
    public ResponseEntity<MovieList> getNowPlaying(@PathVariable Integer page) {
        return movieService.getNowPlaying(page);
    }

    /**
     * Gets a list of Movies according to a search query.
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
