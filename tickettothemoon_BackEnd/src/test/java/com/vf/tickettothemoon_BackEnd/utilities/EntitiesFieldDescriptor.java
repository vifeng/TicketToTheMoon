package com.vf.tickettothemoon_BackEnd.utilities;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptor {

        private FieldDescriptor[] addressResponseFields = {
                        fieldWithPath("street").description(
                                        "The street in the address of the venue of the hall"),
                        fieldWithPath("city").description(
                                        "The city in the address of the venue of the hall"),
                        fieldWithPath("zipcode").description(
                                        "The zipcode in the address of the venue of the hall"),
                        fieldWithPath("country").description(
                                        "The country in the address of the venue of the hall")};
        private FieldDescriptor[] employeeResponseFields = {
                        fieldWithPath("id").description("The employee id in charge of the hall"),
                        fieldWithPath("username")
                                        .description("The employee username in charge of the hall"),
                        fieldWithPath("password")
                                        .description("The employee password in charge of the hall"),
                        fieldWithPath("email")
                                        .description("The employee email in charge of the hall")};
        private FieldDescriptor[] venueResponseFields = {
                        fieldWithPath("id").description("The id of the venue of the hall"),
                        fieldWithPath("name").description("The name of the venue of the hall"),
                        subsectionWithPath("address")
                                        .description("The address of the venue of the hall"),
                        subsectionWithPath("employees")
                                        .description("The employees in charge of the hall"),};
        private FieldDescriptor[] hallResponseFields = {
                        fieldWithPath("id").description("The id of the hall"),
                        fieldWithPath("name").description("The name of the hall"),
                        fieldWithPath("capacityOfHall").description("The capacity of the hall"),
                        subsectionWithPath("venue").description("The venue of the hall")};


        public EntitiesFieldDescriptor() {}

        public FieldDescriptor[] getAddressResponseFields() {
                return addressResponseFields;
        }

        public FieldDescriptor[] getEmployeeResponseFields() {
                return employeeResponseFields;
        }

        public FieldDescriptor[] getVenueResponseFields() {
                return venueResponseFields;
        }

        public FieldDescriptor[] getHallResponseFields() {
                return hallResponseFields;
        }



}
