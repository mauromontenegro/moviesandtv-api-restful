package com.mmg.moviesandtv.api.services;

import com.mmg.moviesandtv.api.models.tv.Tv;
import com.mmg.moviesandtv.api.models.tv.TvList;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TvService {

    ResponseEntity<Tv> getDetails(Integer tvId);

    ResponseEntity<TvList> getPopular(Integer page);

    ResponseEntity<TvList> getTopRated(Integer page);

    ResponseEntity<TvList> getOnTheAir(Integer page);

    ResponseEntity<TvList> getSearchResults(String searchQuery, Integer page);
}
