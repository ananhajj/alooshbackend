package com.dashboard.AlooshDashBoard.api.auth;

import com.dashboard.AlooshDashBoard.entity.dto.AuthResponseDTO;
import com.dashboard.AlooshDashBoard.entity.dto.LoginDto;
import com.dashboard.AlooshDashBoard.security.JWTGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/user")
public class UserApi {
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session){
                System.out.println("Login request received with username: " + loginDto.getUsername());

        Authentication existingAuth= SecurityContextHolder.getContext().getAuthentication();
        if(existingAuth!=null&&existingAuth.isAuthenticated()&&!(existingAuth instanceof AnonymousAuthenticationToken)){
            return new ResponseEntity<>("User is already logged in!", HttpStatus.CONFLICT);
        }
        try {
            Authentication authentication=authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            System.out.println("User authenticated: " + authentication.isAuthenticated());
            System.out.println("Generated JWT: " + token);

            return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return new ResponseEntity<>("Invalid username or password",HttpStatus.UNAUTHORIZED);
        }
    }
}
