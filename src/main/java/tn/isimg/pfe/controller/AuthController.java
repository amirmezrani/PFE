package tn.isimg.pfe.controller;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.BeanIds;
import tn.isimg.pfe.payload.JwtAuthenticationResponse;
import tn.isimg.pfe.payload.LoginRequest;
import tn.isimg.pfe.repository.ComptePatientRepository;
import tn.isimg.pfe.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    @Qualifier(BeanIds.AUTHENTICATION_MANAGER)
    AuthenticationManager authenticationManagerPatient;

    @Autowired
            @Qualifier("authenticationManagerBeanMedecin")
    AuthenticationManager authenticationManagerMedecin;

    @Autowired
    @Qualifier("authenticationManagerBeanAdmin")
    AuthenticationManager authenticationManagerAdmin;


    @Autowired
    ComptePatientRepository comptePatientRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/comptePatient/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManagerPatient.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMotDePasse()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/medecins/signin")
    public ResponseEntity<?> authenticateUserMedecin(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManagerMedecin.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMotDePasse()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/administrateurs/signin")
    public ResponseEntity<?> authenticateUserAdmin(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManagerAdmin.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getMotDePasse()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }


}