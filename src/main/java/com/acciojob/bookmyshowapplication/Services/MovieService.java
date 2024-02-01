package com.acciojob.bookmyshowapplication.Services;


import com.acciojob.bookmyshowapplication.Entities.Movie;
import com.acciojob.bookmyshowapplication.Repository.MovieRepository;
import com.acciojob.bookmyshowapplication.Request.AddMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public String addMovie(AddMovieRequest addMovieRequest){

        Movie movie  = Movie.builder()
                .movieLanguage(addMovieRequest.getMovieLanguage()) // Order of attribute does not matter.
                .movieName(addMovieRequest.getMovieName())
                .genre(addMovieRequest.getGenre())
                .localDate(addMovieRequest.getLocalDate())
                .duration(addMovieRequest.getDuration())
                .rating(addMovieRequest.getRating()).build();

        movie  = movieRepository.save(movie);

        return "The movie has been saved into the Db with The Id "+movie.getMovieId();
    }

    public Movie getMovie(Integer movieId){
        Movie movie  = movieRepository.findById(movieId).get();
        return movie;
    }

}
