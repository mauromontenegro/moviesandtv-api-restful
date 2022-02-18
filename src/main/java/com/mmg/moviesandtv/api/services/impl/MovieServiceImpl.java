package com.mmg.moviesandtv.api.services.impl;

import com.mmg.moviesandtv.api.config.ApiKey;
import com.mmg.moviesandtv.api.models.movies.Movie;
import com.mmg.moviesandtv.api.models.movies.MovieList;
import com.mmg.moviesandtv.api.models.mutual.Cast;
import com.mmg.moviesandtv.api.models.mutual.Video;
import com.mmg.moviesandtv.api.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private RestTemplate restTemplate;

    private ApiKey apiKey = new ApiKey();

    public ResponseEntity<Movie> getDetails(Integer movie_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Movie> entity = new HttpEntity<>(headers);
        ResponseEntity<Movie> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/movie/" + movie_id + "?api_key=" + apiKey.getApiKey()
                        + "&append_to_response=credits,videos", HttpMethod.GET, entity,
                new ParameterizedTypeReference<Movie>() {
                });
        // Setting up poster path, release date and year, and official YouTube trailers correctly.
        return new ResponseEntity<>(setUpMovieInfo(response.getBody()), HttpStatus.OK);
    }

    public ResponseEntity<MovieList> getPopular(Integer page) {
        String type = "popular";
        return getMovieList(type, page);
    }

    public ResponseEntity<MovieList> getTopRated(Integer page) {
        String type = "top_rated";
        return getMovieList(type, page);
    }

    public ResponseEntity<MovieList> getUpcoming(Integer page) {
        String type = "upcoming";
        return getMovieList(type, page);
    }

    public ResponseEntity<MovieList> getNowPlaying(Integer page) {
        String type = "now_playing";
        return getMovieList(type, page);
    }

    public ResponseEntity<MovieList> getMovieList(String type, Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieList> entity = new HttpEntity<>(headers);
        ResponseEntity<MovieList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/movie/" + type + "?api_key=" + apiKey.getApiKey() + "&page=" + page + "&region=AR|US",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<MovieList>() {
                });
        // Setting up poster path, release date and year correctly.
        List<Movie> newList = new ArrayList<>();
        for (Movie movie : response.getBody().getMovies()){
            newList.add(setUpMovieInfo(movie));
        }
        response.getBody().setMovies(newList);
        // Setting up total pages in case it's bigger than 500 - correcting total results.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
            response.getBody().setTotalResults(500*20);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

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

    public ResponseEntity<MovieList> getSearchResults(String searchQuery, Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MovieList> entity = new HttpEntity<>(headers);
        ResponseEntity<MovieList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/search/movie" + "?api_key=" + apiKey.getApiKey() + "&query=" + searchQuery + "&page=" + page,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<MovieList>() {
                });
        // Setting up poster path, release date and year correctly.
        List<Movie> newList = new ArrayList<>();
        for (Movie movie : response.getBody().getMovies()){
            newList.add(setUpMovieInfo(movie));
        }
        response.getBody().setMovies(newList);
        // Setting up total pages in case it's bigger than 500.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}