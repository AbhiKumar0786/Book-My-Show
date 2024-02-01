package com.acciojob.bookmyshowapplication.Entities;


import com.acciojob.bookmyshowapplication.Enums.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "show_seat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer showSeatId;

    private Integer price;

    private boolean isAvailable;

    private boolean foodAttached = false;

    private String seatNo; // These values will come

    @Enumerated(value = EnumType.STRING)
    private SeatType seatType; // from the theaterSeats based on mapping or Seat Structure.

    @JoinColumn
    @ManyToOne
    private Show show;
}
