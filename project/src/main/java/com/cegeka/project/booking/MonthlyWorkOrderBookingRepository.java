package com.cegeka.project.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Collection;
import java.util.Optional;

@Repository
public interface MonthlyWorkOrderBookingRepository extends JpaRepository<MonthlyWorkOrderBookingView, MonthlyWorkOrderBookingView.MonthlyWorkOrderBookingKey> {

    Collection<MonthlyWorkOrderBookingView> findAllByYearMonthOrderByWorkOrder(YearMonth yearMonth);

    Collection<MonthlyWorkOrderBookingView> findAllByWorkOrderOrderByYearMonth(String workOrder);

    Collection<MonthlyWorkOrderBookingView> findAllByWorkOrderInOrderByYearMonth(Collection<String> workOrder);

    Optional<MonthlyWorkOrderBookingView> findByWorkOrderAndYearMonth(String workOrder, YearMonth yearMonth);

    Collection<MonthlyWorkOrderBookingView> findAllByWorkOrderInAndYearMonthOrderByWorkOrder(Collection<String> workOrder, YearMonth yearMonth);

    @Query("SELECT m FROM MonthlyWorkOrderBookingView m WHERE m.workOrder = :workOrder AND m.yearMonth.year = :year ORDER BY m.yearMonth.year, m.yearMonth.month")
    Collection<MonthlyWorkOrderBookingView> findAllByWorkOrderAndYearOrderByYearMonth(@Param("workOrder") String workOrder, @Param("year") int year);

    @Query("SELECT m FROM MonthlyWorkOrderBookingView m WHERE m.workOrder in (:workOrder) AND m.yearMonth.year = :year ORDER BY m.workOrder, m.yearMonth.year, m.yearMonth.month")
    Collection<MonthlyWorkOrderBookingView> findAllByWorkOrderInAndYearOrderByWorkOrderAndYearMonth(@Param("workOrder") Collection<String> workOrder, @Param("year") int year);

    @Query("SELECT m FROM MonthlyWorkOrderBookingView m WHERE m.yearMonth.year = :year ORDER BY m.workOrder, m.yearMonth.year, m.yearMonth.month")
    Collection<MonthlyWorkOrderBookingView> findAllByYearOrderByWorkOrderAndYearMonth(@Param("year") int year);

    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW monthly_work_order_bookings", nativeQuery = true)
    void refreshMonthlyWorkOrderBookings();

}
