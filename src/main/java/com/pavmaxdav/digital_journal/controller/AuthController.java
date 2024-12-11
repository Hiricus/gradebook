package com.pavmaxdav.digital_journal.controller;

import com.pavmaxdav.digital_journal.dto.JwtRequest;
import com.pavmaxdav.digital_journal.dto.JwtResponse;
import com.pavmaxdav.digital_journal.dto.RegisterDTO;
import com.pavmaxdav.digital_journal.enitiy.User;
import com.pavmaxdav.digital_journal.model.RoleRepository;
import com.pavmaxdav.digital_journal.model.UserRepository;
import com.pavmaxdav.digital_journal.service.AdminService;
import com.pavmaxdav.digital_journal.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private JwtUtils jwtUtils;
    private AuthenticationManager authenticationManager;
    private AdminService userService;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager, AdminService userService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    authRequest.getUsername(), authRequest.getPassword()
//            ));
//        } catch (BadCredentialsException e) {
//            return new ResponseEntity<>(new ErrorDto(
//                    HttpStatus.UNAUTHORIZED.value(),"Failed to authorized"),
//                    HttpStatus.UNAUTHORIZED);
//        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtUtils.generateJWT(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        // Check if user exist
        // TO DO

        User user = new User(
                registerDTO.getLogin(),
                registerDTO.getFirstName(),
                registerDTO.getLastName(),
                passwordEncoder.encode(registerDTO.getPassword()),
                registerDTO.getEmail()
        );
        userService.addUser(user);

        return new ResponseEntity<>("User registration succesfull!", HttpStatus.OK);

    }
}
