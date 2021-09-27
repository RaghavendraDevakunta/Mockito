package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.demo.PatientRecordModel.PatientRecord;

import javassist.NotFoundException;

public class PatientRecordController {
	@RestController
	@RequestMapping(value = "/patient")
	public class PatientRecordController {
	    @Autowired PatientRecordRepository patientRecordRepository;
	    // CRUD methods to be added
	}
	@GetMapping
	public List<PatientRecord> getAllRecords() {
	    return patientRecordRepository.findAll();
	}

	@GetMapping(value = "{patientId}")
	public PatientRecord getPatientById(@PathVariable(value="patientId") Long patientId) {
	    return patientRecordRepository.findById(patientId).get();
	}
	@PostMapping
	public PatientRecord createRecord(@RequestBody @Valid PatientRecord patientRecord) {
	    return patientRecordRepository.save(patientRecord);
	}
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	class InvalidRequestException extends RuntimeException {
	    public InvalidRequestException(String s) {
	        super(s);
	    }
	}
	@PutMapping
	public PatientRecord updatePatientRecord(@RequestBody PatientRecord patientRecord) throws NotFoundException {
	    if (patientRecord == null || patientRecord.getPatientId() == null) {
	        throw new InvalidRequestException("PatientRecord or ID must not be null!");
	    }
	    Optional<PatientRecord> optionalRecord = patientRecordRepository.findById(patientRecord.getPatientId());
	    if (optionalRecord.isEmpty()) {
	        throw new NotFoundException("Patient with ID " + patientRecord.getPatientId() + " does not exist.");
	    }
	    PatientRecord existingPatientRecord = optionalRecord.get();

	    existingPatientRecord.setName(patientRecord.getName());
	    existingPatientRecord.setAge(patientRecord.getAge());
	    existingPatientRecord.setAddress(patientRecord.getAddress());
		
	    return patientRecordRepository.save(existingPatientRecord);
	}
	@DeleteMapping(value = "{patientId}")
	public void deletePatientById(@PathVariable(value = "patientId") Long patientId) throws NotFoundException {
	    if (patientRecordRepository.findById(patientId).isEmpty()) {
	        throw new NotFoundException("Patient with ID " + patientId + " does not exist.");
	    }
	    patientRecordRepository.deleteById(patientId);
	}
}
