package com.cegeka.project.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DayBookingRepository extends JpaRepository<DayBooking, UUID> {


    Optional<DayBooking> findByDateAndWorkOrderWorkOrder(LocalDate date, String workOrder);

}
