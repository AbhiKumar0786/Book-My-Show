package com.acciojob.bookmyshowapplication.Repository;

import com.acciojob.bookmyshowapplication.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MovieRepository extends JpaRepository<Movie , Integer> {

    // 2nd type of fetch where you just define the method.
    //no query nothing
    //but naming of method has to be strict.

    Movie findMovieByMovieNameAndMovieLanguage(String MovieName , String MovieLanguage);

    Movie findMovieByMovieName(String MovieName);

    List<Movie> findAllByDurationGreaterThanEqual(double duration);
}
