package tn.isimg.pfe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.isimg.pfe.model.ComptePatient;

import java.util.Optional;

public interface ComptePatientRepository extends JpaRepository<ComptePatient,Long> {


    Optional<ComptePatient> findByEmail(String email);
}
