package com.acciojob.bookmyshowapplication.Services;


import com.acciojob.bookmyshowapplication.Entities.Theater;
import com.acciojob.bookmyshowapplication.Entities.TheaterSeat;
import com.acciojob.bookmyshowapplication.Enums.SeatType;
import com.acciojob.bookmyshowapplication.Repository.TheaterRepository;
import com.acciojob.bookmyshowapplication.Request.AddTheaterRequest;
import com.acciojob.bookmyshowapplication.Request.AddTheaterSeatsRequest;
import com.acciojob.bookmyshowapplication.Transformers.TheaterTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;
    public String addTheater(AddTheaterRequest addTheaterRequest){

        Theater theater  = TheaterTransformer.convertRequestToEntity(addTheaterRequest);

        theater = theaterRepository.save(theater);

        return "Theater has been saved into the Db with theaterId: "+theater.getTheaterId();

    }

    public String addTheaterSeats(AddTheaterSeatsRequest addTheaterSeatsRequest){

        int noOfClassicSeat  = addTheaterSeatsRequest.getNoOfClassicSeats();
        int noOfPremiumSeat  = addTheaterSeatsRequest.getNoOfPremiumSeats();
        Theater theater  = theaterRepository.findById(addTheaterSeatsRequest.getTheaterId()).get();

        int quoClassic  = noOfClassicSeat/5;
        int remClassic  = noOfClassicSeat%5;

        List<TheaterSeat>  theaterSeatList  = new ArrayList<>();

        for(int row  = 1; row<=quoClassic; row++){

            for(int col = 1;col<=5;col++){
                char ch  = (char)('A'+(col-1));
                String seatNo  = row+""+ch;
                TheaterSeat theaterSeatEntity  = TheaterSeat.builder()
                        .theater(theater)
                        .seatType(SeatType.CLASSIC)
                        .seatNo(seatNo)
                        .build();

                theaterSeatList.add(theaterSeatEntity);
            }
        }

        int rowNoForReminder = quoClassic+1;

            for(int col = 1;col<=remClassic;col++){
                char ch  = (char)('A'+(col-1));
                String seatNo  = rowNoForReminder+""+ch;
                TheaterSeat theaterSeatEntity  = TheaterSeat.builder()
                        .theater(theater)
                        .seatType(SeatType.CLASSIC)
                        .seatNo(seatNo)
                        .build();

                theaterSeatList.add(theaterSeatEntity);
            }

        int quoPremium  = noOfPremiumSeat/5;
        int remPremium  = noOfPremiumSeat%5;

        for(int row  = 1; row<=quoPremium; row++){

            for(int col = 1;col<=5;col++){
                char ch  = (char)('A'+(col-1));
                String seatNo  = row+""+ch;
                TheaterSeat theaterSeatEntity  = TheaterSeat.builder()
                        .theater(theater)
                        .seatType(SeatType.PREMIUM)
                        .seatNo(seatNo)
                        .build();

                theaterSeatList.add(theaterSeatEntity);
            }
        }

         rowNoForReminder = quoPremium+1;

        for(int col = 1;col<=remPremium;col++){
            char ch  = (char)('A'+(col-1));
            String seatNo  = rowNoForReminder+""+ch;
            TheaterSeat theaterSeatEntity  = TheaterSeat.builder()
                    .theater(theater)
                    .seatType(SeatType.PREMIUM)
                    .seatNo(seatNo)
                    .build();

            theaterSeatList.add(theaterSeatEntity);
        }

        //Setting the variable of Bidirectional mapping in the parent class.
        theater.setTheaterSeatList(theaterSeatList);

        //Save both parent and child not required: Only save the parent.
        theaterRepository.save(theater);


        return "Theater seats has been added";
    }
}
