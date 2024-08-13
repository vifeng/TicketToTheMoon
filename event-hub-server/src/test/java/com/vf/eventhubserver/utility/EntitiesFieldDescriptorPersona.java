package com.vf.eventhubserver.utility;

import com.vf.eventhubserver.persona.Address;
import com.vf.eventhubserver.persona.Customer;
import com.vf.eventhubserver.persona.Employee;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptorPersona implements EntitiesFieldDescriptor {
  ConstraintDescriptions descAddress = new ConstraintDescriptions(Address.class);
  ConstraintDescriptions descEmployee = new ConstraintDescriptions(Employee.class);
  ConstraintDescriptions descCustomer = new ConstraintDescriptions(Customer.class);

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
}
