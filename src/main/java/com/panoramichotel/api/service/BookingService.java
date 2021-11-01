package com.panoramichotel.api.service;

import com.panoramichotel.api.exceptions.NotFoundException;
import com.panoramichotel.api.model.Booking;

import java.util.Date;

public interface BookingService {
    Long reserve(final String email, final String firstName,
                 final String lastName, Integer noOfPeople,
                 final Date checkInDate, final Date checkOutDate);

    Booking findByBookingId(final Long id);

    Boolean cancelBooking(final Long id) throws NotFoundException;

    Booking checkIfBookingExists(final Date checkInDate);

}
