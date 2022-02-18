package com.mmg.moviesandtv.api.models.mutual;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Info:
 * - https://developers.themoviedb.org/3/movies/get-movie-videos
 * - https://developers.themoviedb.org/3/tv/get-tv-videos
 * List of videos of the movie/tv show.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoList implements Serializable {

    private Integer id;

    @JsonProperty("results")
    private List<Video> videos;
}
