package com.panoramichotel.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "booking")
public class Booking {

    @Id
    @Column(name = "id")
    private Long bookingId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "custommer_id", referencedColumnName = "id")
    private GuestInfo guestInfo;

    @Column(name = "suite_type")
    @Enumerated(EnumType.STRING)
    private Suites suiteType;

    @Column(name = "no_of_people")
    private Integer noOfPeople;

    @Column(name = "check_in_date")
    private Date checkIn;

    @Column(name = "check_out_date")
    private Date checkOut;

    @Column(name = "booking_status")
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}
