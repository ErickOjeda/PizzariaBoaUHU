package app.pizzariatop.controller;

import app.pizzariatop.dto.LoginDTO;
import app.pizzariatop.dto.UsuarioDTO;
import app.pizzariatop.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> logar(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(loginService.logar(loginDTO));
        }catch(AuthenticationException ex) {
            System.out.println(ex.getMessage());

            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("deslogar")
    public ResponseEntity<HttpStatus> logout() {

        SecurityContextHolder.clearContext();
        return new ResponseEntity<>(null, HttpStatus.OK);

    }

}
