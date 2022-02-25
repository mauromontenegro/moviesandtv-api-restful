package com.mmg.moviesandtv.api.services.impl;

import com.mmg.moviesandtv.api.models.movies.Movie;
import com.mmg.moviesandtv.api.models.movies.MovieList;
import com.mmg.moviesandtv.api.models.mutual.Cast;
import com.mmg.moviesandtv.api.models.mutual.Video;
import com.mmg.moviesandtv.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * This service consumes the API (with the API key set as an environment variable) with RestTemplate to get information about Movies.
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    /**
     * Gets Movie details after verifying it.
     * @param movie_id
     * @return
     */
    public ResponseEntity<Movie> getDetails(Integer movie_id) {
        String apiKey = environment.getProperty("TMDB_API_KEY");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Movie> entity = new HttpEntity<>(headers);
        ResponseEntity<Movie> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/movie/" + movie_id + "?api_key=" + apiKey
                        + "&append_to_response=credits,videos", HttpMethod.GET, entity,
                new ParameterizedTypeReference<Movie>() {
                });
        // Setting up poster path, release date and year, and official YouTube trailers correctly.
        return new ResponseEntity<>(setUpMovieInfo(response.getBody()), HttpStatus.OK);
    }

    /**
     * Gets a list of Movies of certain type (popular, top rated, etc.).
     * @param type
     * @param page
     * @return
     */
    public ResponseEntity<MovieList> getMovieList(String type, Integer page) {
        String apiKey = environment.getProperty("TMDB_API_KEY");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieList> entity = new HttpEntity<>(headers);
        ResponseEntity<MovieList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/movie/" + type + "?api_key=" + apiKey + "&page=" + page + "&region=AR|US",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<MovieList>() {
                });
        // Setting up poster path, release date and year correctly.
        List<Movie> newList = new ArrayList<>();
        for (Movie movie : response.getBody().getMovies()) {
            newList.add(setUpMovieInfo(movie));
        }
        response.getBody().setMovies(newList);
        // Setting up total pages in case it's bigger than 500 - correcting total results.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
            response.getBody().setTotalResults(500 * 20);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    /**
     * Gets a list of 'Popular' Movies.
     * @param page
     * @return
     */
    public ResponseEntity<MovieList> getPopular(Integer page) {
        String type = "popular";
        return getMovieList(type, page);
    }

    /**
     * Gets a list of 'Top Rated' Movies.
     * @param page
     * @return
     */
    public ResponseEntity<MovieList> getTopRated(Integer page) {
        String type = "top_rated";
        return getMovieList(type, page);
    }

    /**
     * Gets a list of 'Upcoming' Movies.
     * @param page
     * @return
     */
    public ResponseEntity<MovieList> getUpcoming(Integer page) {
        String type = "upcoming";
        return getMovieList(type, page);
    }

    /**
     * Gets a list of 'Now Playing' Movies.
     * @param page
     * @return
     */
    public ResponseEntity<MovieList> getNowPlaying(Integer page) {
        String type = "now_playing";
        return getMovieList(type, page);
    }

    /**
     * Gets a list of Movies according to a search query.
     * @param searchQuery
     * @param page
     * @return
     */
    public ResponseEntity<MovieList> getSearchResults(String searchQuery, Integer page) {
        String apiKey = environment.getProperty("TMDB_API_KEY");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieList> entity = new HttpEntity<>(headers);
        ResponseEntity<MovieList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/search/movie" + "?api_key=" + apiKey + "&query=" + searchQuery + "&page=" + page,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<MovieList>() {
                });
        // Setting up poster path, release date and year correctly.
        List<Movie> newList = new ArrayList<>();
        for (Movie movie : response.getBody().getMovies()) {
            newList.add(setUpMovieInfo(movie));
        }
        response.getBody().setMovies(newList);
        // Setting up total pages in case it's bigger than 500.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    /**
     * Sets information to a Movie to make it more complete.
     * @param movie
     * @return
     */
    public Movie setUpMovieInfo(Movie movie) {
        // Setting full path to poster and thumbnail.
        if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()) {
            movie.setThumbnail("https://image.tmdb.org/t/p/w342" + movie.getPosterPath());
            movie.setPosterPath("https://image.tmdb.org/t/p/original" + movie.getPosterPath());
        } else {
            movie.setPosterPath("/img/upcoming.png");
            movie.setThumbnail("/img/upcoming.png");
        }
        // Setting release date if null and setting release year from release date.
        if (movie.getReleaseDate() == null || movie.getReleaseDate().isEmpty()) {
            movie.setReleaseDate("Unknown");
            movie.setReleaseYear("Unknown");
        } else {
            movie.setReleaseYear(movie.getReleaseDate().substring(0, 4));
        }
        // Setting official YouTube trailers only.
        List<Video> newList = new ArrayList();
        // Only when is called from "getDetails(...)":
        if (movie.getVideos() != null) {
            for (Video video : movie.getVideos().getVideos()) {
                if (video.getType().equals("Trailer") && video.getSite().equals("YouTube") && video.isOfficial()) {
                    video.setLink("https://www.youtube.com/watch?v=" + video.getKey());
                    newList.add(video);
                }
            }
            movie.getVideos().setVideos(newList);
        }
        // Setting up main cast.
        if (movie.getCredits() != null) {
            int min = Math.min(movie.getCredits().getCast().size(), 4);
            List<Cast> mainCast = new ArrayList<>();
            for (int i = 0; i < min; i++) {
                mainCast.add(movie.getCredits().getCast().get(i));
            }
            movie.getCredits().setMainCast(mainCast);
        }
        // Filling null attributes.
        if (movie.getOverview() == null || movie.getOverview().isEmpty()) {
            movie.setOverview("Not available.");
        }
        return movie;
    }
}