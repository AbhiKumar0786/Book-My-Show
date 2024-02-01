package com.acciojob.bookmyshowapplication.Transformers;

import com.acciojob.bookmyshowapplication.Entities.Theater;
import com.acciojob.bookmyshowapplication.Request.AddTheaterRequest;

public class TheaterTransformer {

    public static Theater convertRequestToEntity(AddTheaterRequest addTheaterRequest){
        Theater theater  = Theater.builder()
                .name(addTheaterRequest.getName())
                .address(addTheaterRequest.getAddress())
                .noOfScreens(addTheaterRequest.getNoOfScreens())
                .build();

        return theater;
    }
}
