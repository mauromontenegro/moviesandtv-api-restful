package com.mmg.moviesandtv.api.services.impl;

import com.mmg.moviesandtv.api.config.ApiKey;
import com.mmg.moviesandtv.api.models.mutual.Cast;
import com.mmg.moviesandtv.api.models.mutual.Video;
import com.mmg.moviesandtv.api.models.tv.Tv;
import com.mmg.moviesandtv.api.models.tv.TvList;
import com.mmg.moviesandtv.api.services.TvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class TvServiceImpl implements TvService {

    @Autowired
    private RestTemplate restTemplate;

    private ApiKey apiKey = new ApiKey();

    public ResponseEntity<Tv> getDetails(Integer tvId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Tv> entity = new HttpEntity<>(headers);
        ResponseEntity<Tv> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/tv/" + tvId + "?api_key=" + apiKey.getApiKey()
                        + "&append_to_response=credits,videos", HttpMethod.GET, entity,
                new ParameterizedTypeReference<Tv>() {
                });
        // Setting up poster path, release date and year, and official YouTube trailers correctly.
        return new ResponseEntity<>(setUpTvInfo(response.getBody()), HttpStatus.OK);
    }

    public ResponseEntity<TvList> getPopular(Integer page) {
        String type = "popular";
        return getTvShowList(type, page);
    }

    public ResponseEntity<TvList> getTopRated(Integer page) {
        String type = "top_rated";
        return getTvShowList(type, page);
    }

    public ResponseEntity<TvList> getOnTheAir(Integer page) {
        String type = "on_the_air";
        return getTvShowList(type, page);
    }

    public ResponseEntity<TvList> getTvShowList(String type, Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TvList> entity = new HttpEntity<>(headers);
        ResponseEntity<TvList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/tv/" + type + "?api_key=" + apiKey.getApiKey() + "&page=" + page + "&region=AR|US",
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<TvList>() {
                });
        // Setting up poster path, release date and year correctly.
        List<Tv> newList = new ArrayList<>();
        for (Tv tv : response.getBody().getTvShows()){
            newList.add(setUpTvInfo(tv));
        }
        response.getBody().setTvShows(newList);
        // Setting up total pages in case it's bigger than 500 - correcting total results.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
            response.getBody().setTotalResults(500*20);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }

    public Tv setUpTvInfo(Tv tv) {
        // Setting full path to poster and thumbnail.
        if (tv.getPosterPath() != null && !tv.getPosterPath().isEmpty()) {
            tv.setThumbnail("https://image.tmdb.org/t/p/w342" + tv.getPosterPath());
            tv.setPosterPath("https://image.tmdb.org/t/p/original" + tv.getPosterPath());
        } else {
            tv.setPosterPath("/img/upcoming.png");
            tv.setThumbnail("/img/upcoming.png");
        }
        // Setting release date if null and setting release year from release date.
        if (tv.getReleaseDate() == null || tv.getReleaseDate().isEmpty()) {
            tv.setReleaseDate("Unknown");
            tv.setReleaseYear("Unknown");
        } else {
            tv.setReleaseYear(tv.getReleaseDate().substring(0,4));
        }
        // Setting official YouTube trailers only.
        List<Video> newList = new ArrayList();
        // Only when is called from "getDetails(...)":
        if (tv.getVideos() != null) {
            for (Video video : tv.getVideos().getVideos()) {
                if (video.getType().equals("Trailer") && video.getSite().equals("YouTube") && video.isOfficial()) {
                    video.setLink("https://www.youtube.com/watch?v=" + video.getKey());
                    newList.add(video);
                }
            }
            tv.getVideos().setVideos(newList);
        }
        // Setting up main cast.
        if (tv.getCredits() != null) {
            int min= Math.min(tv.getCredits().getCast().size(),4);
            List<Cast> mainCast = new ArrayList<>();
            for (int i = 0; i < min; i++) {
                mainCast.add(tv.getCredits().getCast().get(i));
            }
            tv.getCredits().setMainCast(mainCast);
        }
        // Filling null attributes.
        if (tv.getOverview() == null || tv.getOverview().isEmpty()) {
            tv.setOverview("Not available.");
        }
        return tv;
    }

    public ResponseEntity<TvList> getSearchResults(String searchQuery, Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TvList> entity = new HttpEntity<>(headers);
        ResponseEntity<TvList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/search/tv" + "?api_key=" + apiKey.getApiKey() + "&query=" + searchQuery + "&page=" + page,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<TvList>() {
                });
        // Setting up poster path, release date and year correctly.
        List<Tv> newList = new ArrayList<>();
        for (Tv tv : response.getBody().getTvShows()){
            newList.add(setUpTvInfo(tv));
        }
        response.getBody().setTvShows(newList);
        // Setting up total pages in case it's bigger than 500.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
