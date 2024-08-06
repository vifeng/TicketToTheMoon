package com.vf.eventhubserver.utility;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import com.vf.eventhubserver.persona.Address;
import com.vf.eventhubserver.persona.Customer;
import com.vf.eventhubserver.persona.Employee;
import com.vf.eventhubserver.venue.Hall;
import com.vf.eventhubserver.venue.Venue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptor {
  ConstraintDescriptions descAddress = new ConstraintDescriptions(Address.class);
  ConstraintDescriptions descEmployee = new ConstraintDescriptions(Employee.class);
  ConstraintDescriptions descCustomer = new ConstraintDescriptions(Customer.class);
  ConstraintDescriptions descHall = new ConstraintDescriptions(Hall.class);
  ConstraintDescriptions descVenue = new ConstraintDescriptions(Venue.class);

  public FieldDescriptor[] generateAddressFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "street", "",
            "city", "",
            "zipcode", "",
            "country", "");
    return generateFields(
        includeId,
        "address",
        descAddress,
        customDescriptions,
        new String[] {"street", "city", "zipcode", "country"});
  }

  public FieldDescriptor[] generateEmployeeFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "The employee ID in charge of the venue. ",
            "username", "The employee username in charge of the venue. ",
            "password", "",
            "email", "The employee email in charge of the venue. ");
    return generateFields(
        includeId,
        "employee",
        descEmployee,
        customDescriptions,
        new String[] {"username", "password", "email"});
  }

  public FieldDescriptor[] generateCustomerFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "firstName", "",
            "lastName", "",
            "username", "",
            "email", "",
            "phoneNumber", "",
            "address", "",
            "creditCardNumber", "");
    return generateFields(
        includeId,
        "customer",
        descCustomer,
        customDescriptions,
        new String[] {
          "firstName", "lastName", "username", "email", "phoneNumber", "address", "creditCardNumber"
        });
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

  private FieldDescriptor[] generateFields(
      boolean includeId,
      String entityType,
      ConstraintDescriptions descProvider,
      Map<String, String> customDescriptions,
      String[] fieldNames) {
    List<FieldDescriptor> fields = new ArrayList<>();
    if (includeId) {
      String idDescription =
          customDescriptions.getOrDefault("id", "").isEmpty()
              ? "The " + entityType + " ID. "
              : customDescriptions.get("id");
      fields.add(
          fieldWithPath("id")
              .description(
                  idDescription
                      + collectionToDelimitedString(
                          descProvider.descriptionsForProperty("id"), ". ")));
    }
    for (String fieldName : fieldNames) {
      String fieldDescription =
          customDescriptions.getOrDefault(fieldName, "").isEmpty()
              ? "The " + fieldName + " of the " + entityType + ". "
              : customDescriptions.get(fieldName);
      fields.add(
          fieldWithPath(fieldName)
              .description(
                  fieldDescription
                      + collectionToDelimitedString(
                          descProvider.descriptionsForProperty(fieldName), ". ")));
    }
    return fields.toArray(new FieldDescriptor[0]);
  }
}
