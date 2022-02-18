package com.mmg.moviesandtv.api.models.movies;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Info:
 * - https://developers.themoviedb.org/3/movies/get-now-playing
 * - https://developers.themoviedb.org/3/movies/get-popular-movies
 * - https://developers.themoviedb.org/3/movies/get-top-rated-movies
 * - https://developers.themoviedb.org/3/movies/get-upcoming
 * List of movies with page number, list of movies of that page and total of pages with popular movies.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieList implements Serializable {

    private Integer page;

    @JsonProperty("results")
    private List<Movie> movies;

    @JsonProperty("total_results")
    private Integer totalResults;

    @JsonProperty("total_pages")
    private Integer totalPages;
}
