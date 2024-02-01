package com.acciojob.bookmyshowapplication.Request;


import lombok.Data;

@Data
public class AddTheaterSeatsRequest {

    private Integer noOfClassicSeats;
    private Integer noOfPremiumSeats;
    private Integer theaterId;
}
