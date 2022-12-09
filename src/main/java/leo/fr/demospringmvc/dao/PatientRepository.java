package leo.fr.demospringmvc.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import leo.fr.demospringmvc.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	public Page<Patient> findByNameContains(String motcle , Pageable pageable);

}
