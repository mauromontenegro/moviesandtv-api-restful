package com.mmg.moviesandtv.api.services;

import com.mmg.moviesandtv.api.models.person.Person;
import com.mmg.moviesandtv.api.models.person.PersonList;
import org.springframework.http.ResponseEntity;

public interface PersonService {

    ResponseEntity<Person> getDetails(Integer person_id);

    ResponseEntity<PersonList> getSearchResults(String searchQuery, Integer page);
}
