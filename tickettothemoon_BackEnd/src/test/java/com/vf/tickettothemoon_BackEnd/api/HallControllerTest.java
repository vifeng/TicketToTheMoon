package com.vf.tickettothemoon_BackEnd.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.HallRepository;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class HallControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    String url = "http://localhost:8080/api/";
    @Autowired
    private HallRepository hallRepository;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .build();
    }

    // test getHallById
    @Test
    public void hallsGetByIdExample() throws Exception {
        this.mockMvc.perform(get(url + "halls/{id}", 1L).accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk());
    }

    // test updateHall
    // test patchHall
    // test deleteHall
    // test createHallForVenueId
    // test getHallsByVenueId

}
