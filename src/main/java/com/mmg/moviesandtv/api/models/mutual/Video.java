package com.mmg.moviesandtv.api.models.mutual;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Info:
 * - https://developers.themoviedb.org/3/movies/get-movie-videos
 * - https://developers.themoviedb.org/3/tv/get-tv-videos
 */

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video implements Serializable {

    private String id;

    private boolean official;

    private String type;

    private String key;

    private String site;

    private String link;
}
