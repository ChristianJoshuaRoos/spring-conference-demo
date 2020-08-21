package com.pluralsight.conferencedemo.repositories;

import com.pluralsight.conferencedemo.models.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TimeSlotJpaRepository extends JpaRepository<TimeSlot, Long> {

    List<TimeSlot> findByTimeSlotDateBefore(Date date);

    List<TimeSlot> findByStartTimeAfter(Time startTime);

    List<TimeSlot> findByStartTimeBetween(Time time1, Time time2);
}
