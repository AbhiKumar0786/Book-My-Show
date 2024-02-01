package com.acciojob.bookmyshowapplication.Entities;

import com.acciojob.bookmyshowapplication.Enums.Genre;
import com.acciojob.bookmyshowapplication.Enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data //Use for getter,Setter and ToString Method
@Builder // used to create the object of this class anywhere but @AllArgsConstructor is also mandatory
@AllArgsConstructor
@NoArgsConstructor

public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer movieId;

    @Column(unique = true , nullable = false)
    private String movieName;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @Enumerated(value = EnumType.STRING)
    private Language movieLanguage;

    private double rating;

    private LocalDate localDate;

    private Double duration;

    @OneToMany(mappedBy = "movie" , cascade = CascadeType.ALL)
    private List<Show> showList  = new ArrayList<>();

}
