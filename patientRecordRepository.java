package com.example.demo;

import static org.hamcrest.Matchers.*;

import java.awt.PageAttributes.MediaType;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

public class patientRecordRepository {
	@Test
	public void getAllRecords_success() throws Exception {
	    List<PatientRecord> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));
	    
	    Mockito.when(patientRecordRepository.findAll()).thenReturn(records);
	    
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/patient")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(3)))
	            .andExpect(jsonPath("$[2].name", is("Jane Doe")));
	}
	@Test
	public void getPatientById_success() throws Exception {
	    Mockito.when(patientRecordRepository.findById(RECORD_1.getPatientId())).thenReturn(java.util.Optional.of(RECORD_1));

	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/patient/1")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.name", is("Rayven Yor")));
	}
	
}
