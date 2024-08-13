package com.vf.eventhubserver.utility;

import com.vf.eventhubserver.show.Event;
import com.vf.eventhubserver.show.SessionEvent;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptorShow implements EntitiesFieldDescriptor {
  ConstraintDescriptions descEvent = new ConstraintDescriptions(Event.class);
  ConstraintDescriptions descSessionEvent = new ConstraintDescriptions(SessionEvent.class);

  public FieldDescriptor[] generateEventFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "name", "",
            "description", "",
            "dateStart", "",
            "dateEnd", "",
            "closedDay", "",
            "imageUrl", "");
    return generateFields(
        includeId,
        "event",
        descEvent,
        customDescriptions,
        new String[] {"name", "description", "dateStart", "dateEnd", "closedDay", "imageUrl"});
  }

  public FieldDescriptor[] generateSessionEventFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "dateAndTimeStartSessionEvent", "The date and time when the session event starts. ",
            "durationInMinutes", "The duration of the session event in minutes. ",
            "event", "Event for which the session event is scheduled. ",
            "configurationHall",
                "The configuration hall where the session event will take place. ");
    return generateFields(
        includeId,
        "sessionEvent",
        descSessionEvent,
        customDescriptions,
        new String[] {
          "dateAndTimeStartSessionEvent", "durationInMinutes", "event", "configurationHall"
        });
  }
}
