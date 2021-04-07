package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long> {

}
