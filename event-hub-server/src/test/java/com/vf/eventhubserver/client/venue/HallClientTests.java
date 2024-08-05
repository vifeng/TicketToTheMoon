package com.vf.eventhubserver.client.venue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.vf.eventhubserver.venue.Hall;
import com.vf.eventhubserver.venue.Venue;
import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(
    webEnvironment = WebEnvironment.RANDOM_PORT,
    properties = "spring.config.name=application-test")
@Sql(scripts = {"/testdb/data.sql"})
public class HallClientTests {
  @Autowired private TestRestTemplate testRestTemplate;

  @Test
  public void createHallForVenueId() {
    String url = "/api/venues/{venueId}/halls";
    Venue venue = testRestTemplate.getForObject("/api/venues/1", Venue.class);
    // missing venue in database
    Hall hall = new Hall("hall1", 300, venue);
    URI newHallUri = testRestTemplate.postForLocation(url, hall, 1);
    Hall retrievedHall = testRestTemplate.getForObject(newHallUri, Hall.class);
    assertEquals(hall.getName(), retrievedHall.getName());
  }
}
