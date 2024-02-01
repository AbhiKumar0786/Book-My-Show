package com.acciojob.bookmyshowapplication.Request;

import lombok.Data;

@Data
public class AddShowSeatRequest {

    private int priceOfClassicSeats;
    private int priceOfPremiumSeats;
    private int showId;
}
