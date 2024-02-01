package com.acciojob.bookmyshowapplication.Services;


import com.acciojob.bookmyshowapplication.Entities.Show;
import com.acciojob.bookmyshowapplication.Entities.ShowSeat;
import com.acciojob.bookmyshowapplication.Entities.Ticket;
import com.acciojob.bookmyshowapplication.Entities.User;
import com.acciojob.bookmyshowapplication.Repository.ShowRepository;
import com.acciojob.bookmyshowapplication.Repository.TicketRepository;
import com.acciojob.bookmyshowapplication.Repository.UserRepository;
import com.acciojob.bookmyshowapplication.Request.BookTicketRequest;
import com.acciojob.bookmyshowapplication.Response.ShowTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    public String bookTicket(BookTicketRequest bookTicketRequest) throws Exception{

        Show show  = showRepository.findById(bookTicketRequest.getShowId()).get();

        //Check for seat available or not.
        List<ShowSeat> showSeatList = show.getShowSeatList();
        int totalBookingAmount  = 0;

        for(String seatNoToBeBooked : bookTicketRequest.getSeatList()){

            for(ShowSeat showSeat : showSeatList){

                if(showSeat.getSeatNo().equals(seatNoToBeBooked) &&
                   showSeat.getSeatType().equals(bookTicketRequest.getSeatType())){

                    if(showSeat.isAvailable()){
                        showSeat.setAvailable(Boolean.FALSE);
                        totalBookingAmount += showSeat.getPrice();
                    }
                    else{
                        throw new Exception("Seat No "+showSeat.getSeatNo()+" is already booked.");
                    }
                }
            }
        }
        User user  = userRepository.findByEmailId(bookTicketRequest.getEmailId());
        //All the ticket were available and booked.

        Ticket ticket  = Ticket.builder()
                .seatNosBooked(bookTicketRequest.getSeatList().toString())
                .totalAmountPaid(totalBookingAmount)
                .show(show)
                .user(user)
                .build();

        show.getTicketList().add(ticket); // Bidirectional Mapping.

        user.getTicketList().add(ticket);
        ticket  = ticketRepository.save(ticket);

        return "This is the ticket with ticketId "+ticket.getTicketId();

    }

    private String generateBookedSeats(List<String> bookedSeats){
        return bookedSeats.toString();
    }

    public ShowTicketResponse viewTicket(Integer ticketId){
        Ticket ticket = ticketRepository.findById(ticketId).get();

        Show show  = ticket.getShow();
        String movieName  = show.getMovie().getMovieName();
        String theaterInfo  = show.getTheater().getName()+" "+show.getTheater().getAddress();
        String bookedSeats  = ticket.getSeatNosBooked();

        ShowTicketResponse showTicketResponse  = ShowTicketResponse.builder()
                .movieName(movieName)
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .theaterInfo(theaterInfo)
                .seatNos(bookedSeats)
                .totalAmt(ticket.getTotalAmountPaid())
                .build();

        String emailId  = ticket.getUser().getEmailId();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("abhiyami0991@gmail.com");
        simpleMailMessage.setTo(emailId);
        simpleMailMessage.setSubject("Movie ticket confirmation !!");
        simpleMailMessage.setText(showTicketResponse.toString());

        javaMailSender.send(simpleMailMessage);


        return showTicketResponse;
    }
}
