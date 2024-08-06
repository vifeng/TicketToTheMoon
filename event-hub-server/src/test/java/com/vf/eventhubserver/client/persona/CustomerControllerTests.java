package com.vf.eventhubserver.client.persona;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.persona.Address;
import com.vf.eventhubserver.persona.Customer;
import com.vf.eventhubserver.persona.CustomerMapper;
import com.vf.eventhubserver.persona.CustomerService;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptor;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
public class CustomerControllerTests {
  // implement tests for CustomerController
  private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  String baseUrl = "http://localhost:8080/api/";
  EntitiesFieldDescriptor entitiesFieldDescriptor = new EntitiesFieldDescriptor();

  @Autowired private CustomerService customerService;
  @Autowired private CustomerMapper customerMapper;

  @BeforeEach
  public void setUp(
      WebApplicationContext webApplicationContext,
      RestDocumentationContextProvider restDocumentation) {
    this.mockMvc =
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(
                document(
                    "{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())))
            .build();
  }

  @Test
  public void testGetAllCustomers() throws Exception {
    ResultActions request = mockMvc.perform(get(baseUrl + "customers")).andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[*]", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is("Macaron")))
        .andExpect(jsonPath("$[0].lastName", is("Le glouton")))
        .andDo(
            document(
                "customers-get",
                responseFields(fieldWithPath("[]").description("An array of Customers"))
                    .andWithPrefix("[].", entitiesFieldDescriptor.generateCustomerFields(true))
                    .andWithPrefix(
                        "[].address.", entitiesFieldDescriptor.generateAddressFields(false))));
  }

  @Test
  public void testGetCustomerById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "customers/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk());

    request.andReturn().getResponse().getHeader("Location");

    request
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is("Macaron")))
        .andExpect(jsonPath("$.lastName", is("Le glouton")))
        .andDo(
            document(
                "customer-get-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the customer to be retrieved")),
                responseFields(entitiesFieldDescriptor.generateCustomerFields(true))
                    .andWithPrefix(
                        "address.", entitiesFieldDescriptor.generateAddressFields(false))));
  }

  // testing createCustomer
  @Test
  public void testCreateCustomer() throws Exception {
    Address address = new Address("1234 Sesame Street", "New York", "10001", "USA");
    Customer customer =
        new Customer(
            "Elmo",
            "Furry Animal",
            "redDevil",
            "elmo@gmail.com",
            "0601020304",
            address,
            "1234567890");

    ResultActions request =
        this.mockMvc
            .perform(
                post(baseUrl + "customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isCreated())
            .andDo(
                document(
                    "customer-create",
                    requestFields(entitiesFieldDescriptor.generateCustomerFields(false))
                        .andWithPrefix(
                            "address.", entitiesFieldDescriptor.generateAddressFields(false))));

    String location = request.andReturn().getResponse().getHeader("Location");
    this.mockMvc
        .perform(get(location))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.firstName", is("Elmo")))
        .andExpect(jsonPath("$.lastName", is("Furry Animal")));
  }

  @Test
  public void testDeleteCustomer() throws Exception {
    this.mockMvc
        .perform(delete(baseUrl + "customers/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNoContent())
        .andDo(
            document(
                "customer-delete-by-id",
                pathParameters(
                    parameterWithName("id").description("The id of the customer to be deleted"))));

    this.mockMvc
        .perform(get(baseUrl + "customers/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isNotFound());
  }
}
