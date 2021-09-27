package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

public class PatientRecordModel {

	@Entity
	@Table(name = "patient_record")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public class PatientRecord {
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long patientId;
	    
	    @NonNull
	    private String name;
	 
	    @NonNull
	    private Integer age;
	    
	    @NonNull 
	    private String address;

		public PatientRecord save(PatientRecord existingPatientRecord) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	@Repository
	public interface PatientRecordRepository extends JpaRepository<PatientRecord, Long> {}
}
