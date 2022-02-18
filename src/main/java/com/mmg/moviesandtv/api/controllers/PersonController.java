package com.mmg.moviesandtv.api.controllers;

import com.mmg.moviesandtv.api.models.person.Person;
import com.mmg.moviesandtv.api.models.person.PersonList;
import com.mmg.moviesandtv.api.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Info: https://developers.themoviedb.org/3/people/get-person-details
     * @param person_id
     * @return
     */
    @GetMapping("/{person_id}")
    public ResponseEntity<Person> getDetails(@PathVariable Integer person_id) {
        return personService.getDetails(person_id);
    }

    /**
     * Info: https://developers.themoviedb.org/3/search/search-people
     * @param searchQuery
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<PersonList> searchPerson(@RequestParam String searchQuery, @RequestParam Integer page) {
        return personService.getSearchResults(searchQuery, page);
    }
}
