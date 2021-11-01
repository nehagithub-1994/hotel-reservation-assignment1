package com.panoramichotel.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.panoramichotel.api.controller.BookingController;
import com.panoramichotel.api.dto.BookingDTO;
import com.panoramichotel.api.service.BookingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;


    @Test
    @DisplayName("Test booking success")
    void addBooking() throws Exception {
        final String url = "/booking/presidential-suite";
        final Resource resource = new ClassPathResource("testData.json");

        final File file = resource.getFile();
        final BookingDTO bookingDTO = objectMapper.readValue(file, new TypeReference<>() {
        });
        final String requestBody = objectMapper.writeValueAsString(bookingDTO);

        when(this.bookingService.reserve(any(), any(), any(), any(), any(), any())).thenReturn(101L);

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Test for overlapping booking")
    void shouldReturnBadRequestIfDatesOverlapAndPeopleExceeds() throws Exception {
        final String url = "/booking/presidential-suite";
        final Resource resource = new ClassPathResource("invalid-booking.json");

        final File file = resource.getFile();
        final BookingDTO bookingDTO = objectMapper.readValue(file, new TypeReference<>() {
        });
        final String requestBody = objectMapper.writeValueAsString(bookingDTO);

        when(this.bookingService.reserve(any(), any(), any(), any(), any(), any())).thenReturn(101L);

        this.mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Cancel booking success")
    void cancelBooking() throws Exception {
        final String url = "/booking/cancel/26972";

        when(this.bookingService.cancelBooking(26972L)).thenReturn(true);

        this.mockMvc.perform(post(url))
                .andExpect(status().isOk());
    }

}
