package com.jairlopes.bookstoremanager.controller;

import com.jairlopes.bookstoremanager.builder.PublisherDTOBuilder;
import com.jairlopes.bookstoremanager.dto.PublisherDTO;
import com.jairlopes.bookstoremanager.service.PublisherService;
import com.jairlopes.bookstoremanager.utils.JsonConversionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

    private final static String PUBLISHER_API_URL_PATH = "/api/v1/publishers";

    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setup() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        when(publisherService.create(expectedCreatedPublisherDTO)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        Long expectedCreatedPublisherDTOId = expectedCreatedPublisherDTO.getId();
        when(publisherService.findById(expectedCreatedPublisherDTOId)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(get(PUBLISHER_API_URL_PATH + "/" + expectedCreatedPublisherDTOId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTOId.intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenGETListIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        when(publisherService.findAll()).thenReturn(Collections.singletonList(expectedCreatedPublisherDTO));

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonConversionUtils.asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$[0].code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenDELETEIsCalledThenNoContentStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedPublisherToDelete = publisherDTOBuilder.buildPublisherDTO();
        Long expectedPublisherIdToDelete = expectedPublisherToDelete.getId();

        doNothing().when(publisherService).delete(expectedPublisherIdToDelete);

        mockMvc.perform(delete(PUBLISHER_API_URL_PATH + "/" + expectedPublisherIdToDelete)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
