package tn.isimg.pfe.security;

import tn.isimg.pfe.enumeration.RoleEnum;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.ComptePatient;
import tn.isimg.pfe.repository.ComptePatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComptePatientDetailsService implements UserDetailsService {

    @Autowired
    ComptePatientRepository comptePatientRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // Let people login with either  email
        ComptePatient user = comptePatientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with  email : " + email)
                );

        return UserPrincipal.create(user.getId(), user.getEmail(),user.getMotDePasse(), RoleEnum.ROLE_PATIENT);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        ComptePatient user = comptePatientRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user.getId(), user.getEmail(),user.getMotDePasse(), RoleEnum.ROLE_PATIENT);
    }
}
