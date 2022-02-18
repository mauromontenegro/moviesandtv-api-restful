package com.mmg.moviesandtv.api.services;

import com.mmg.moviesandtv.api.models.movies.Movie;
import com.mmg.moviesandtv.api.models.movies.MovieList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MovieService {

    ResponseEntity<Movie> getDetails(Integer movie_id);

    ResponseEntity<MovieList> getPopular(Integer page);

    ResponseEntity<MovieList> getTopRated(Integer page);

    ResponseEntity<MovieList> getUpcoming(Integer page);

    ResponseEntity<MovieList> getNowPlaying(Integer page);

    ResponseEntity<MovieList> getSearchResults(String searchQuery, Integer page);
}
