package com.example.demo;

import static org.hamcrest.CoreMatchers.notNullValue;

import java.awt.PageAttributes.MediaType;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class PatientRecord {

	@Test
	public void createRecord_success() throws Exception {
	    PatientRecord record = PatientRecord.builder()
	            .name("John Doe")
	            .age(47)
	            .address("New York USA")
	            .build();

	    Mockito.when(patientRecordRepository.save(record)).thenReturn(record);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(record));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.name", is("John Doe")));
	    }
	
	
	private static Object builder() {
		// TODO Auto-generated method stub
		return null;
	}


	@Test
	public void updatePatientRecord_success() throws Exception {
	    PatientRecord updatedRecord = PatientRecord.builder()
	            .patientId(1l)
	            .name("Rayven Zambo")
	            .age(23)
	            .address("Cebu Philippines")
	            .build();

	    Mockito.when(patientRecordRepository.findById(RECORD_1.getPatientId())).thenReturn(Optional.of(RECORD_1));
	    Mockito.when(patientRecordRepository.save(updatedRecord)).thenReturn(updatedRecord);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRecord));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", notNullValue()))
	            .andExpect(jsonPath("$.name", is("Rayven Zambo")));
	}
	@Test
	public void updatePatientRecord_nullId() throws Exception {
	    PatientRecord updatedRecord = PatientRecord.builder()
	            .name("Sherlock Holmes")
	            .age(40)
	            .address("221B Baker Street")
	            .build();

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRecord));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isBadRequest())
	            .andExpect(result ->
	                assertTrue(result.getResolvedException() instanceof PatientRecordController.InvalidRequestException))
	    .andExpect(result ->
	        assertEquals("PatientRecord or ID must not be null!", result.getResolvedException().getMessage()));
	    }

	@Test
	public void updatePatientRecord_recordNotFound() throws Exception {
	    PatientRecord updatedRecord = PatientRecord.builder()
	            .patientId(5l)
	            .name("Sherlock Holmes")
	            .age(40)
	            .address("221B Baker Street")
	            .build();

	    Mockito.when(patientRecordRepository.findById(updatedRecord.getPatientId())).thenReturn(null);

	    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/patient")
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON)
	            .content(this.mapper.writeValueAsString(updatedRecord));

	    mockMvc.perform(mockRequest)
	            .andExpect(status().isBadRequest())
	            .andExpect(result ->
	                assertTrue(result.getResolvedException() instanceof NotFoundException))
	    .andExpect(result ->
	        assertEquals("Patient with ID 5 does not exist.", result.getResolvedException().getMessage()));
	}
	@Test
	public void deletePatientById_success() throws Exception {
	    Mockito.when(patientRecordRepository.findById(RECORD_2.getPatientId())).thenReturn(Optional.of(RECORD_2));

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/patient/2")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

	@Test
	public void deletePatientById_notFound() throws Exception {
	    Mockito.when(patientRecordRepository.findById(5l)).thenReturn(null);

	    mockMvc.perform(MockMvcRequestBuilders
	            .delete("/patient/2")
	            .contentType(MediaType.APPLICATION_JSON))
	    .andExpect(status().isBadRequest())
	            .andExpect(result ->
	                    assertTrue(result.getResolvedException() instanceof NotFoundException))
	    .andExpect(result ->
	            assertEquals("Patient with ID 5 does not exist.", result.getResolvedException().getMessage()));
	}
}


