package com.panoramichotel.api.repository;

import com.panoramichotel.api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where ?1 between b.checkIn and b.checkOut" )
    Booking checkIfBookingExists(Date checkInDate);
}
