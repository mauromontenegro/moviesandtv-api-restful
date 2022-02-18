package com.mmg.moviesandtv.api.models.mutual;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Info: https://developers.themoviedb.org/3/movies/get-movie-credits
 * Movie credits (only actors and director).
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Credits implements Serializable {

    private Integer id;

    private List<Cast> cast;

    private List<Cast> mainCast;

}
