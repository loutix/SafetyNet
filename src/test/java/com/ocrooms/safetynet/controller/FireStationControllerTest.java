package com.ocrooms.safetynet.controller;

import com.ocrooms.safetynet.entities.Firestation;
import com.ocrooms.safetynet.service.FireStationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private void setUpPerTest() {
        firestationsSet.add(new Firestation("Paris", 1));
        firestationsSet.add(new Firestation("Bordeaux", 2));
        firestationsSet.add(new Firestation("Lyon", 3));
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

}