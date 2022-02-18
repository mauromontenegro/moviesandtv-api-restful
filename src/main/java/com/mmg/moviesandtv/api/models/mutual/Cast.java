package com.mmg.moviesandtv.api.models.mutual;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Info: https://developers.themoviedb.org/3/movies/get-movie-credits
 * Movie credits (only actors and director).
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cast implements Serializable {

    private Integer id;

    private String name;

    private String character;

    @JsonProperty("profile_path")
    private String image;

    private String title;

    @JsonProperty("release_date")
    private String movieReleaseDate;

    @JsonProperty("first_air_date")
    private String tvReleaseDate;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("media_type")
    private String mediaType;

    public void setImage(String image) {
        if(image != null && !image.isEmpty()) {
            this.image = "https://image.tmdb.org/t/p/original" + image;
        } else {
            this.image = "/img/user.png";
        }
    }

    public void setPosterPath(String posterPath) {
        if (posterPath != null && !posterPath.isEmpty()){
            this.posterPath = "https://image.tmdb.org/t/p/original" + posterPath;
        } else {
            this.posterPath = "/img/upcoming.png";
        }
    }
}
