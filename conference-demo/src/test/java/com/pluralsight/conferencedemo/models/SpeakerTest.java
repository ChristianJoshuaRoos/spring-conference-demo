package com.pluralsight.conferencedemo.models;

import com.pluralsight.conferencedemo.repositories.SpeakerJpaRepository;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpeakerTest {
    @Autowired
    private SpeakerJpaRepository jpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testFind() throws Exception {
        Speaker speaker = jpaRepository.getOne(1L);
        assertNotNull(speaker);
    }

    @Test
    @Transactional
    public void testSaveAndGetAndDelete() throws Exception {
        Speaker s = new Speaker();
        s.setCompany("Pluralsight");
        s.setFirstName("Dan");
        s.setLastName("Bunker");
        s.setTitle("Author");
        s.setSpeakerBio("Consulting and mentoring");
        s = jpaRepository.saveAndFlush(s);

        // clear the persistence context so we don't return the previously cached location object
        // this is a test only thing and normally doesn't need to be done in prod code
        entityManager.clear();

        Speaker otherSpeaker = jpaRepository.getOne(s.getSpeakerId());
        assertEquals("Dan", otherSpeaker.getFirstName());

        jpaRepository.deleteById(otherSpeaker.getSpeakerId());
    }

    @Test
    public void testJpaAnd() throws Exception{
        List<Speaker> speakers = jpaRepository.findByFirstNameAndLastName("Justin", "Clark");
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testJpaOr() throws Exception{
        List<Speaker> speakers = jpaRepository.findByFirstNameOrLastName("Justin", "Clark");
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testJpaNull() throws Exception{
        List<Speaker> speakers = jpaRepository.findBySpeakerPhotoNull();
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testJpaIn() throws Exception{
        List<String> companies = new ArrayList<String>();
        companies.add("National Bank");
        companies.add("Contoso");
        List<Speaker> speakers = jpaRepository.findByCompanyIn(companies);
        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testJpaIgnoreCase() throws Exception{
        List<Speaker> speakers = jpaRepository.findByCompanyIgnoreCase("national bank");

        for (Speaker speaker : speakers){
            System.out.print(speaker.getFirstName() + " ");
            System.out.println(speaker.getLastName());
        }

        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testJpaOrderBy() throws Exception{
        List<Speaker> speakers = jpaRepository.findByLastNameOrderByFirstNameAsc("Perry");

        for (Speaker speaker : speakers){
            System.out.print(speaker.getFirstName() + " ");
            System.out.println(speaker.getLastName());
        }

        assertTrue(speakers.size() > 0);
    }

    @Test
    public void testJpaFirst() throws Exception{
        Speaker speaker = jpaRepository.findFirstByFirstName("James");

        System.out.print(speaker.getFirstName() + " ");
        System.out.println(speaker.getLastName());

        assertTrue(speaker.getFirstName().equals("James"));
    }

}
