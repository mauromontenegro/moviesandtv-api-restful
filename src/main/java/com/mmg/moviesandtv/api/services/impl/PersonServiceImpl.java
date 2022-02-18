package com.mmg.moviesandtv.api.services.impl;

import com.mmg.moviesandtv.api.config.ApiKey;
import com.mmg.moviesandtv.api.models.person.Person;
import com.mmg.moviesandtv.api.models.person.PersonList;
import com.mmg.moviesandtv.api.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.Period;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private RestTemplate restTemplate;

    private ApiKey apiKey = new ApiKey();

    public ResponseEntity<Person> getDetails(Integer person_id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Person> entity = new HttpEntity<>(headers);
        ResponseEntity<Person> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/person/" + person_id + "?api_key=" + apiKey.getApiKey()
                        + "&append_to_response=combined_credits", HttpMethod.GET, entity,
                //, HttpMethod.GET, entity,
                new ParameterizedTypeReference<Person>() {
                });
        return new ResponseEntity<>(setUpPersonInfo(response.getBody()), HttpStatus.OK);
    }

    private Person setUpPersonInfo(Person person) {
        // Image and thumbnail
        if(person.getImage() != null && !person.getImage().isEmpty()) {
            person.setThumbnail("https://image.tmdb.org/t/p/w185" + person.getImage());
            person.setImage("https://image.tmdb.org/t/p/original" + person.getImage());
        } else {
            person.setImage("/img/user.png");
            person.setThumbnail("/img/user.png");
        }
        // Gender type
        switch (person.getGender()) {
            case 0:
                person.setGenderType("Not specified");
                break;
            case 1:
                person.setGenderType("Female");
                break;
            case 2:
                person.setGenderType("Male");
                break;
            case 3:
                person.setGenderType("Non-binary");
                break;
        }
        // Place Of Birth
        if (person.getBirth() == null || person.getBirth().isEmpty()){
            person.setBirth("Unknown");
        }
        // Age
        String startDate = person.getBirthday();
        String finalDate;
        if (person.getDeathDay() != null && !person.getDeathDay().isEmpty()) {
            finalDate = person.getDeathDay();
        } else {
            finalDate = LocalDate.now().toString();
        }
        LocalDate sdate = LocalDate.parse(startDate);
        LocalDate pdate = LocalDate.parse(finalDate);

        LocalDate ssdate = LocalDate.of(sdate.getYear(), sdate.getMonth(), sdate.getDayOfMonth());
        LocalDate ppdate = LocalDate.of(pdate.getYear(), pdate.getMonth(), pdate.getDayOfMonth());

        Period period = Period.between(ssdate, ppdate);
        person.setAge(period.getYears());
        // Filling null attributes.
        if (person.getBiography() == null || person.getBiography().isEmpty()){
            person.setBiography("Not available.");
        }
        return person;
    }

    public ResponseEntity<PersonList> getSearchResults(String searchQuery, Integer page) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PersonList> entity = new HttpEntity<>(headers);
        ResponseEntity<PersonList> response = restTemplate.exchange(
                "https://api.themoviedb.org/3/search/person" + "?api_key=" + apiKey.getApiKey() + "&query=" + searchQuery + "&page=" + page,
                HttpMethod.GET, entity,
                new ParameterizedTypeReference<PersonList>() {
                });
        // Setting up person image:
        for (Person person : response.getBody().getPeople()) {
            if(person.getImage() != null && !person.getImage().isEmpty()) {
                person.setImage("https://image.tmdb.org/t/p/original" + person.getImage());
            } else {
                person.setImage("/img/user.png");
            }
        }
        // Setting up total pages in case it's bigger than 500.
        if (response.getBody().getTotalPages() > 500) {
            response.getBody().setTotalPages(500);
        }
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
