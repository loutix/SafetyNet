package com.ocrooms.safetynet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    private Set<Firestation> firestationsSet = new HashSet<>();

    @BeforeEach
    public void setUpPerTest() {
        firestationsSet.add(new Firestation("Paris", 1));
        firestationsSet.add(new Firestation("Bordeaux", 2));
        firestationsSet.add(new Firestation("Lyon", 3));
        firestationsSet.add(new Firestation("Lyon", 4));
    }

    @Test
    @DisplayName(("Get all firestations"))
    public void testGestFirestations() throws Exception {

        when(fireStationService.index()).thenReturn(firestationsSet);
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(firestationsSet.size()))
                .andExpect(jsonPath("$[0].address", is("Lyon")))
                .andExpect(jsonPath("$[0].station", is(3)));
    }

    @Test
    @DisplayName(("Show a specific fire station"))
    public void testShowFirestations() throws Exception {

        String targetAddress = "Lyon";

        List<Firestation> lyonFirestationList = firestationsSet.stream()
                .filter(firestation -> firestation.getAddress().equals(targetAddress))
                .toList();

        when(fireStationService.show(targetAddress)).thenReturn(lyonFirestationList);
        mockMvc.perform(get("/firestations/show").param("address", targetAddress))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(lyonFirestationList.size()))
                .andExpect(jsonPath("$[0].address", is(targetAddress)))
                .andExpect(jsonPath("$[0].station").value(is(3)))
                .andExpect(jsonPath("$[1].address", is(targetAddress)))
                .andExpect(jsonPath("$[1].station").value(is(4)));
        //.andDo(print());

    }

    @Test
    @DisplayName(("Create a  fire station"))
    public void testCreateFirestation() throws Exception {

        Firestation newFirestation = new Firestation("Paris", 12);
        firestationsSet.add(newFirestation);

        when(fireStationService.create(newFirestation)).thenReturn(newFirestation);
        mockMvc.perform(post("/firestations")
                        .content(new ObjectMapper().writeValueAsString(newFirestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName(("Update a  fire station"))
    public void testUpdateFirestation() throws Exception {

        String targetAddress = "Paris";
        Firestation.FirestationUpdateRequest updateFirestation = new Firestation.FirestationUpdateRequest(1, 10);

        doNothing().when(fireStationService).update(targetAddress, updateFirestation);
        mockMvc.perform(put("/firestations")
                        .param("address", targetAddress)
                        .content(new ObjectMapper().writeValueAsString(updateFirestation))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(fireStationService, times(1)).update(targetAddress, updateFirestation);
    }

    @Test
    @DisplayName(("Delete a  fire station"))
    public void testDeleteFirestation() throws Exception {

        String targetAddress = "Paris";
        Integer targetStation = 1;

        doNothing().when(fireStationService).delete(targetAddress, targetStation);
        mockMvc.perform(delete("/firestations")
                        .param("address", targetAddress)
                        .param("station", String.valueOf(targetStation)))
                .andExpect(status().isNoContent());

        verify(fireStationService, times(1)).delete(targetAddress, targetStation);
    }

}