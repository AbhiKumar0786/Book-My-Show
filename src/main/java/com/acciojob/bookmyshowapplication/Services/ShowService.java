package com.acciojob.bookmyshowapplication.Services;

import com.acciojob.bookmyshowapplication.CustomException.InvalidMovieException;
import com.acciojob.bookmyshowapplication.Entities.*;
import com.acciojob.bookmyshowapplication.Enums.SeatType;
import com.acciojob.bookmyshowapplication.InvalidTheaterException;
import com.acciojob.bookmyshowapplication.Repository.MovieRepository;
import com.acciojob.bookmyshowapplication.Repository.ShowRepository;
import com.acciojob.bookmyshowapplication.Repository.TheaterRepository;
import com.acciojob.bookmyshowapplication.Request.AddShowRequest;
import com.acciojob.bookmyshowapplication.Request.AddShowSeatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private MovieRepository movieRepository;
    public String addShow(AddShowRequest addShowRequest) throws Exception{

        Movie movie  =  movieRepository.findMovieByMovieName(addShowRequest.getMovieName());

        if(movie == null){
            throw new InvalidMovieException("Movie name entered does not exist in the DB.");
        }

        Optional<Theater> optionalTheater  =  theaterRepository.findById(addShowRequest.getTheaterId());

        if(optionalTheater.isEmpty()){
            throw new InvalidTheaterException("Theater id entered invalid does not exist in the Db.");
        }

        Theater theater = optionalTheater.get();

        Show show = new Show(addShowRequest.getShowDate() , addShowRequest.getShowTime());

        show.setMovie(movie);
        show.setTheater(theater);

        //Due to Bidirectional we have to this into the list of this parent.
        movie.getShowList().add(show);
        theater.getShowList().add(show);

        show = showRepository.save(show);

        return "The show has been created with the showId: "+show.getShowId();

    }



    public String addShowSeats(AddShowSeatRequest addShowSeatRequest){

        Show show  = showRepository.findById(addShowSeatRequest.getShowId()).get();
        Theater theater  = show.getTheater();
        List<TheaterSeat> theaterSeatList  = theater.getTheaterSeatList();

        List<ShowSeat> showSeatList = new ArrayList<>();

        for(TheaterSeat theaterSeat : theaterSeatList){

            String seatNo  = theaterSeat.getSeatNo();
            SeatType seatType = theaterSeat.getSeatType();

            ShowSeat showSeat  = ShowSeat.builder()
                    .seatNo(seatNo)
                    .seatType(seatType)
                    .foodAttached(false)
                    .isAvailable(true)
                    .show(show).build();

            if(seatType.equals(seatType.CLASSIC)){
                showSeat.setPrice(addShowSeatRequest.getPriceOfClassicSeats());
            }
            else{
                showSeat.setPrice(addShowSeatRequest.getPriceOfPremiumSeats());
            }
            showSeatList.add(showSeat);
        }

        show.setShowSeatList(showSeatList);

        showRepository.save(show);

        return "Show seats has been added to the system.";
    }
}
