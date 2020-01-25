package com.isaackennedy.curso.resources;

import com.isaackennedy.curso.dto.EmailDTO;
import com.isaackennedy.curso.security.JWTUtil;
import com.isaackennedy.curso.security.UserSS;
import com.isaackennedy.curso.services.AuthService;
import com.isaackennedy.curso.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<String> forgot(@Valid @RequestBody EmailDTO objDTO) {
        String pass = authService.sendNewPassword(objDTO.getEmail());
        return ResponseEntity.ok(pass);
    }

}
