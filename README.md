# moviesandtv-api-restful
<h1> 💻 REST API to consume data from The Movie Database API.</h1>

<p>
  This repository contains the code I used to consume "The Movie Database API" for my website <a href="https://bit.ly/mtvdb" target="_blank"><b>Movies and TV Info</b></a>.
</p>
<p>
  ✔ Main features:
  <ul>
    <li>The project was created in <b>Java 11</b> with the <b>Spring framework</b> (version 2.6.3).</li>
    <li>Each "Service" class consumes the API using Spring's "<b>Rest Template</b>" and uses the Models to provide the information as a response.</li>
    <li>The <b>API key</b> required by the TMDB API is set as an <i>environment variable</i>.</li>
    <li>This project doesn't have security settings yet, but I plan to do so in the future.</li>
    <li>Why did I created this API? Well, the short answer is: because I wanted to learn how to consume an API in Java. The longer version is that I wanted to handle the information to be more complete and easier to handle in the frontend.</li>
    <li> You can see the documentation in the "<b>doc</b>" folder (Postman).</li>
  </ul>
</p>
<p>
  ✔ Maven dependencies:
  <ul>
    <li>Spring boot starter web.</li>
    <li>Devtools.</li>
    <li>Lombok.</li>
  </ul>
</p>
<p>
  🔸 Do you want to try it? The API was deployed on Heroku (you don't need an API key to use it). Here are some requests you can try:
  <ul>
    <li><a href="https://moviesandtv-api-restful.herokuapp.com/api/movie/634649" target="_blank">Get Movie details - Spider Man: No Way Home (2021)</a>.</li>
    <li><a href="https://moviesandtv-api-restful.herokuapp.com/api/tv/popular/1" target="_blank">Get 'Popular TV Shows' List - Page 1</a>.</li>
    <li><a href="https://moviesandtv-api-restful.herokuapp.com/api/person/1136406" target="_blank">Get Tom Holland's profile</a>.</li>
    <li><a href="https://moviesandtv-api-restful.herokuapp.com/api/movie/search?searchQuery=avengers&page=1" target="_blank">Get results for 'avengers' search query in Movies</a>.</li>
  </ul>
</p>
