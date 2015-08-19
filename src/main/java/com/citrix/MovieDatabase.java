package com.citrix;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MovieDatabase {

    public List<Movie> getMovies() {
        return Arrays.asList(
                new Movie("American Sniper", "Client Eastwood"),
                new Movie("The Imitation Game", "Morten Tyldum"),
                new Movie("Selma", "Ava DuVernay"),
                new Movie("The Theory of Everything", "James Marsh"),
                new Movie("Whiplash", "Damien Chazelle"),
                new Movie("Boyhood", "Richard Linklater"),
                new Movie("The Grand Budapest Hotel", "Wes Anderson"));
    }

}
