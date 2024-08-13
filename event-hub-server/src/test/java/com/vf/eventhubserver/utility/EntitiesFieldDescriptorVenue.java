package com.vf.eventhubserver.utility;

import com.vf.eventhubserver.venue.CategorySpatial;
import com.vf.eventhubserver.venue.ConfigurationHall;
import com.vf.eventhubserver.venue.Hall;
import com.vf.eventhubserver.venue.Seat;
import com.vf.eventhubserver.venue.SeatStatus;
import com.vf.eventhubserver.venue.Venue;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptorVenue implements EntitiesFieldDescriptor {
  // venue
  ConstraintDescriptions descCategorySpatial = new ConstraintDescriptions(CategorySpatial.class);
  ConstraintDescriptions descConfigurationHall =
      new ConstraintDescriptions(ConfigurationHall.class);
  ConstraintDescriptions descHall = new ConstraintDescriptions(Hall.class);
  ConstraintDescriptions descSeat = new ConstraintDescriptions(Seat.class);
  ConstraintDescriptions descSeatStatus = new ConstraintDescriptions(SeatStatus.class);
  ConstraintDescriptions descVenue = new ConstraintDescriptions(Venue.class);

  public FieldDescriptor[] generateCategorySpatialFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "name", "");
    return generateFields(
        includeId,
        "categorySpatial",
        descCategorySpatial,
        customDescriptions,
        new String[] {"name"});
  }

  public FieldDescriptor[] generateConfigurationHallFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id",
            "",
            "name",
            "",
            "capacityOfConfiguration",
            "The actual number of seats available within this configuration. Exemple: for a Hall of 100, a seated configuration will have a capacity of 50. A standing one will have a capacity of 100. ",
            "hall",
            "");
    return generateFields(
        includeId,
        "configurationHall",
        descConfigurationHall,
        customDescriptions,
        new String[] {"name", "capacityOfConfiguration", "hall"});
  }

  public FieldDescriptor[] generateHallFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "name", "",
            "capacityOfHall", "The maximum legal capacity of the hall. ",
            "venue", "");
    return generateFields(
        includeId,
        "hall",
        descHall,
        customDescriptions,
        new String[] {"name", "capacityOfHall", "venue"});
  }

  public FieldDescriptor[] generateSeatFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "isSeated", "True if the seat is seated, false if it is standing. ",
            "rowNo", "The row number of the seat. ",
            "seatNo", "The seat number for the row. ",
            "configurationHall",
                "The configuration hall to which the seat belongs. It defines the capacity of the configuration and a name. ",
            "categorySpatial", "",
            "categoryTariff", "",
            "seatStatus", "");
    return generateFields(
        includeId,
        "seat",
        descSeat,
        customDescriptions,
        new String[] {
          "isSeated",
          "rowNo",
          "seatNo",
          "configurationHall",
          "categorySpatial",
          "categoryTariff",
          "seatStatus"
        });
  }

  public FieldDescriptor[] generateSeatStatusFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "name", "");
    return generateFields(
        includeId, "seatStatus", descSeatStatus, customDescriptions, new String[] {"name"});
  }

  public FieldDescriptor[] generateVenueFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "name", "",
            "address", "",
            "employees", "The employees in charge of the venue. ");
    return generateFields(
        includeId,
        "venue",
        descVenue,
        customDescriptions,
        new String[] {"name", "address", "employees"});
  }
}
