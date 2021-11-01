package com.panoramichotel.api.controller;

import com.panoramichotel.api.dto.BookingDTO;
import com.panoramichotel.api.exceptions.InvalidBookingException;
import com.panoramichotel.api.exceptions.NotFoundException;
import com.panoramichotel.api.model.Booking;
import com.panoramichotel.api.service.BookingService;
import com.panoramichotel.api.util.Util;
import com.panoramichotel.api.util.ValidationErrorProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/booking")
public class BookingController implements ValidationErrorProcessor {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/presidential-suite")
    private ResponseEntity<?> bookHotel(@Validated @RequestBody BookingDTO bookingDTO, final BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                return processValidationErrors(bindingResult);
            }
            validateBooking(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate(), bookingDTO.getNoOfPeople());
            final Long bookingId = bookingService.reserve(bookingDTO.getEmail(), bookingDTO.getFirstName(), bookingDTO.getLastName(), bookingDTO.getNoOfPeople(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate());
            String responseString = "Reservation is successful.Booking Id is " + bookingId + "\n" + "Find your reservation details here: \n" + "localhost:8080/booking/reservation-details/" + bookingId + "\n" + "You can cancel reservation here: " + "localhost:8080/booking/cancel/" + bookingId;
            log.info("Booking Successful");
            return new ResponseEntity<>(responseString, HttpStatus.CREATED);
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
        Booking booking = bookingService.checkIfBookingExists(checkInDate);

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
    private ResponseEntity<?> bookHotel(@PathVariable Long reservationId) {
        try {
            return ResponseEntity.ok(bookingService.cancelBooking(reservationId));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
