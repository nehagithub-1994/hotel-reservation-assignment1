package com.panoramichotel.api.service;

import com.panoramichotel.api.exceptions.NotFoundException;
import com.panoramichotel.api.model.Booking;
import com.panoramichotel.api.model.BookingStatus;
import com.panoramichotel.api.model.GuestInfo;
import com.panoramichotel.api.model.Suites;
import com.panoramichotel.api.repository.BookingRepository;
import com.panoramichotel.api.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    @Override
    @Transactional
    public Long reserve(final String email, final String firstName, final String lastName, final Integer noOfPeople, final Date checkInDate, final Date checkOutDate) {

        final Booking booking = new Booking();

        final Long bookingId = Util.getFiveDigitRandomNo();

        booking.setBookingId(bookingId);

        booking.setCheckIn(checkInDate);
        booking.setCheckOut(checkOutDate);
        booking.setNoOfPeople(noOfPeople);
        booking.setSuiteType(Suites.PRESIDENTIAL_SUITE);
        booking.setBookingStatus(BookingStatus.BOOKED);

        final GuestInfo guestInfo = new GuestInfo();
        guestInfo.setId((int) Math.random());
        guestInfo.setEmail(email);
        guestInfo.setFirstName(firstName);
        guestInfo.setLastName(lastName);

        booking.setGuestInfo(guestInfo);
        Booking result = bookingRepository.save(booking);
        return result.getBookingId();
    }

    @Override
    public Booking findByBookingId(final Long id) {
        return bookingRepository.findById(id).get();
    }

    @Override
    public Boolean cancelBooking(Long id) throws NotFoundException {

        Optional<Booking> result = bookingRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException(String.format("No Bookings found for given id %d", id));
        }
        Booking booking = result.get();
        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public Booking checkIfBookingExists(final Date checkInDate) {
        return bookingRepository.checkIfBookingExists(checkInDate);
    }
}
