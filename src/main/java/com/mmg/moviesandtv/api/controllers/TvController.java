package com.mmg.moviesandtv.api.controllers;

import com.mmg.moviesandtv.api.models.tv.Tv;
import com.mmg.moviesandtv.api.models.tv.TvList;
import com.mmg.moviesandtv.api.services.TvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tv")
public class TvController {

    @Autowired
    private TvService tvService;

    /**
     * Info: https://developers.themoviedb.org/3/tv/get-tv-details
     * @param tv_id
     * @return
     */
    @GetMapping("/{tv_id}")
    public ResponseEntity<Tv> getDetails(@PathVariable Integer tv_id) {
        return tvService.getDetails(tv_id);
    }

    /**
     * Info: https://developers.themoviedb.org/3/tv/get-popular-tv-shows
     * @param page
     * @return
     */
    @GetMapping("/popular/{page}")
    public ResponseEntity<TvList> getPopular(@PathVariable Integer page) {
        return tvService.getPopular(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/tv/get-top-rated-tv
     * @param page
     * @return
     */
    @GetMapping("/top_rated/{page}")
    public ResponseEntity<TvList> getTopRated(@PathVariable Integer page) {
        return tvService.getTopRated(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/tv/get-tv-on-the-air
     * @param page
     * @return
     */
    @GetMapping("/on_the_air/{page}")
    public ResponseEntity<TvList> getOnTheAir(@PathVariable Integer page) {
        return tvService.getOnTheAir(page);
    }

    /**
     * Info: https://developers.themoviedb.org/3/search/search-tv-shows
     * @param searchQuery
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<TvList> searchTv(@RequestParam String searchQuery, @RequestParam Integer page) {
        return tvService.getSearchResults(searchQuery, page);
    }
}
