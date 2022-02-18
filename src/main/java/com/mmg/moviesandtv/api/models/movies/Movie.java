package com.mmg.moviesandtv.api.models.movies;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmg.moviesandtv.api.models.mutual.Credits;
import com.mmg.moviesandtv.api.models.mutual.Genre;
import com.mmg.moviesandtv.api.models.mutual.VideoList;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Info: https://developers.themoviedb.org/3/movies/get-movie-details
 * Movie details (only a few of them).
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie implements Serializable {

    private Integer id;

    private String title;

    @JsonProperty("poster_path")
    private String posterPath;

    private String thumbnail;

    private String tagline;

    private String overview;

    @JsonProperty("original_language")
    private String originalLanguage;

    private String status;

    @JsonProperty("release_date")
    private String releaseDate;

    private String releaseYear;

    private Integer runtime;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    private List<Genre> genres;

    private Credits credits;

    private VideoList videos;
}