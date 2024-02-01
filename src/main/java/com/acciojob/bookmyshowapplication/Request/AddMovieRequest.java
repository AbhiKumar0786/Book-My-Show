package com.acciojob.bookmyshowapplication.Request;

import com.acciojob.bookmyshowapplication.Enums.Genre;
import com.acciojob.bookmyshowapplication.Enums.Language;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AddMovieRequest {

    private String movieName;
    private Genre genre;
    private Language movieLanguage;
    private double rating;
    private LocalDate localDate;
    private Double duration;
}
