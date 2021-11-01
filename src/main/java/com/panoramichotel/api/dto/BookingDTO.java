package com.panoramichotel.api.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class BookingDTO implements Serializable {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private Integer noOfPeople;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkInDate;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date checkOutDate;

}
