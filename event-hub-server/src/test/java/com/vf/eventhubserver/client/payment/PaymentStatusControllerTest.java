package com.vf.eventhubserver.client.payment;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.eventhubserver.payment.PaymentStatusRepository;
import com.vf.eventhubserver.utility.EntitiesFieldDescriptorPayment;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(properties = "spring.config.name=application-test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
@Sql(scripts = {"/testdb/data.sql"})
public class PaymentStatusControllerTest {

  @Autowired private ObjectMapper objectMapper;
  private MockMvc mockMvc;
  String baseUrl = "http://localhost:8080/api/paymentStatus";
  private EntitiesFieldDescriptorPayment entitiesFieldDescriptorPayment =
      new EntitiesFieldDescriptorPayment();
  @Autowired PaymentStatusRepository paymentRepositoryStatus;

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

  // TODo: getAll, getById, getByName

  @Test
  public void getAllPaymentsStatus() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(
                document(
                    "paymentstatus-get",
                    responseFields(fieldWithPath("[]").description("An array of payments status"))
                        .andWithPrefix(
                            "[].",
                            entitiesFieldDescriptorPayment.generatePaymentStatusFields(true))));

    request
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].paymentStatusName").value("paid"));
    ;
  }

  @Test
  public void getPaymentStatusById() throws Exception {
    ResultActions request =
        this.mockMvc
            .perform(get(baseUrl + "/{id}", 1L).accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andDo(
                document(
                    "paymentstatus-get-id",
                    pathParameters(
                        parameterWithName("id")
                            .description("The id of the payment status to be retrieved"))));
  }

  @Test
  public void getPaymentStatusByName() throws Exception {

    this.mockMvc
        .perform(get(baseUrl + "/status/{status}", "paid").accept(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andDo(
            document(
                "paymentstatus-get-name",
                pathParameters(
                    parameterWithName("status")
                        .description("The status name of the payment status to be retrieved"))));
  }
}
