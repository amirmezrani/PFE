package tn.isimg.pfe.security;

import tn.isimg.pfe.enumeration.RoleEnum;
import tn.isimg.pfe.exception.ExistException;
import tn.isimg.pfe.exception.NonValideException;
import tn.isimg.pfe.exception.ResourceNotFoundException;
import tn.isimg.pfe.model.ComptePatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.isimg.pfe.model.Medecin;
import tn.isimg.pfe.repository.MedecinRepository;

@Service
public class MedecinDetailsService implements UserDetailsService {

    @Autowired
    MedecinRepository medecinRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        // Let people login with either  email
        Medecin user = medecinRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with  email : " + email)
                );
        if(user.getValider().equals(false)){
            throw new NonValideException("non valid medecins");
        }
        else {

        System.out.println(user.getMotDePasse());

        return UserPrincipal.create(user.getId(), user.getEmail(),user.getMotDePasse(), RoleEnum.ROLE_MEDECIN);
    }
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        Medecin user = medecinRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user.getId(), user.getEmail(),user.getMotDePasse(), RoleEnum.ROLE_MEDECIN);
    }
}