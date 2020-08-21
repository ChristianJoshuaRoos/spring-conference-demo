package com.pluralsight.conferencedemo.models;

import com.pluralsight.conferencedemo.repositories.TimeSlotJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TimeSlotTest {

    @Autowired
    private TimeSlotJpaRepository jpaRepository;

    @Test
    public void testJpaBefore() throws Exception {
        String dateHyphenPattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern((dateHyphenPattern));
        LocalDate date = LocalDate.of(2020, 4, 10);

        List<TimeSlot> timeSlots = jpaRepository.findByTimeSlotDateBefore(Date.valueOf(dateFormatter.format(date)));
        assertTrue(timeSlots.size() > 0);
    }

    @Test
    public void testJpaAfter() throws Exception {
        String timeColonPattern = "HH:mm:ss";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime time = LocalTime.of(12, 00, 00);

        List<TimeSlot> timeSlots = jpaRepository.findByStartTimeAfter(Time.valueOf(timeFormatter.format(time)));
        assertTrue(timeSlots.size() > 0);
    }

    @Test
    public void testJpaBetween() throws Exception {
        String timeColonPattern = "HH:mm:ss";
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        LocalTime time1 = LocalTime.of(8, 30, 00);
        LocalTime time2 = LocalTime.of(11, 20, 00);

        List<TimeSlot> timeSlots = jpaRepository.findByStartTimeBetween(
                Time.valueOf(timeFormatter.format(time1)), Time.valueOf(timeFormatter.format(time2)));
        assertTrue(timeSlots.size() > 0);
    }
}
