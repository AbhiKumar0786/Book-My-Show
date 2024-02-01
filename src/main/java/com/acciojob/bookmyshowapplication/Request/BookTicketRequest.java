package com.acciojob.bookmyshowapplication.Request;


import com.acciojob.bookmyshowapplication.Enums.SeatType;
import lombok.Data;

import java.util.List;

@Data
public class BookTicketRequest {

    public int showId;
    public List<String>  seatList;
    private SeatType seatType;
    public String emailId;
}
