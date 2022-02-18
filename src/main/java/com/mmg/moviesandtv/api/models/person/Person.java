package com.mmg.moviesandtv.api.models.person;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mmg.moviesandtv.api.models.mutual.Credits;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Info: https://developers.themoviedb.org/3/people/get-person-details
 * Person details (only a few of them).
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

    private Integer id;

    private String name;

    private String birthday;

    private String biography;

    @JsonProperty("profile_path")
    private String image;

    private String thumbnail;

    private String banner;

    private Integer gender;

    private String genderType;

    @JsonProperty("place_of_birth")
    private String birth;

    @JsonProperty("known_for_department")
    private String knownFor;

    @JsonProperty("deathday")
    private String deathDay;

    private Integer age;

    @JsonProperty("combined_credits")
    private Credits credits;
}
