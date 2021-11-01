package com.panoramichotel.api.controller;

import com.panoramichotel.api.exceptions.InvalidBookingException;
import com.panoramichotel.api.exceptions.NotFoundException;
import com.panoramichotel.api.model.Booking;
import com.panoramichotel.api.service.BookingService;
import com.panoramichotel.api.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/presidential-suite")
    private ResponseEntity<?> bookHotel(@RequestParam String email, @RequestParam String firstName,
                                        @RequestParam String lastName, @RequestParam Integer noOfPeople,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date checkInDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date checkOutDate) {
        try {
            validateBooking(checkInDate, checkOutDate, noOfPeople);
            final Long bookingId = bookingService.reserve(email, firstName, lastName, noOfPeople, checkInDate, checkOutDate);
            String responseString = "Reservation is successful.Booking Id is " + bookingId + "\n" + "Find your reservation details here: \n" + "localhost:8080/booking/reservation-details/" + bookingId + "\n" + "You can cancel reservation here: " + "localhost:8080/booking/cancel/" + bookingId;
            log.info("Booking Successful");
            return ResponseEntity.ok(responseString);
        } catch (InvalidBookingException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    private void validateBooking(Date checkInDate, Date checkOutDate, Integer noOfPeople) throws InvalidBookingException {
        int days = Util.daysBetween(checkInDate, checkOutDate);
        if (noOfPeople > 3 || days > 3) {
            throw new InvalidBookingException(String.format("Booking is allowed upto 3 people for 3 days"));
        }
        Booking booking = bookingService.checkIfBookingExists(checkInDate, checkOutDate);

        if (booking != null) {
            log.error("Booking already exists!");
            throw new InvalidBookingException(String.format("Booking already exists for given date"));
        }
    }

    @GetMapping("/reservation-details/{reservationId}")
    private ResponseEntity<?> getReservationDetails(@PathVariable Long reservationId) throws NotFoundException {

        Booking booking = bookingService.findByBookingId(reservationId);
        if (booking != null) {
            return ResponseEntity.ok(bookingService.findByBookingId(reservationId));
        } else {
            throw new NotFoundException(String.format("No Bookings found for given reservation Id %s", reservationId));
        }
    }

    @PostMapping("/cancel/{reservationId}")
    private ResponseEntity<Boolean> bookHotel(@PathVariable Long reservationId) {
        return ResponseEntity.ok(bookingService.cancelBooking(reservationId));
    }

}
