package com.mmg.moviesandtv.api.models.tv;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmg.moviesandtv.api.models.mutual.Credits;
import com.mmg.moviesandtv.api.models.mutual.Genre;
import com.mmg.moviesandtv.api.models.mutual.VideoList;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tv implements Serializable {

    private Integer id;

    private List<Genre> genres;

    private String name;

    private String tagline;

    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    private String thumbnail;

    private String status;

    @JsonProperty("first_air_date")
    private String releaseDate;

    private String releaseYear;

    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;

    @JsonProperty("number_of_seasons")
    private Integer numberOfSeasons;

    @JsonProperty("vote_average")
    private Double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    private Credits credits;

    private VideoList videos;

    // TODO: create Seasons model and its controller.
}
