package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.model.Patient;

import java.util.Set;

public interface PatientRepository extends JpaRepository<Patient,Long> {

    Set<Patient> findByComptePatient(ComptePatient comptePatient);
}
