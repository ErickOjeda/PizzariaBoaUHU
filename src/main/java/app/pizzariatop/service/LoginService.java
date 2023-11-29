package app.pizzariatop.service;

import app.pizzariatop.dto.LoginDTO;
import app.pizzariatop.convert.UsuarioDTOConvert;
import app.pizzariatop.dto.UsuarioDTO;
import app.pizzariatop.entity.Usuario;
import app.pizzariatop.repository.LoginRepository;
import app.pizzariatop.security.JwtServiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    private LoginRepository repository;
    @Autowired
    private JwtServiceGenerator jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDTOConvert usuarioDTOConvert;

    public UsuarioDTO logar(LoginDTO loginDTO) {
        System.out.println("a");
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
            loginDTO.getUsername(),
            loginDTO.getPassword()
          )
        );
        System.out.println("b");
        Usuario user = repository.findByUsername(loginDTO.getUsername()).orElseThrow();
        System.out.println("c");
        var jwtToken = jwtService.generateToken(user);
        System.out.println("d");
        return toUsuarioDTO(user,jwtToken);
    }

    public UsuarioDTO toUsuarioDTO(Usuario usuario, String token){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setRole(usuario.getRole());
        usuarioDTO.setToken(token);
        usuarioDTO.setUsername(usuario.getUsername());
        return usuarioDTO;
    }


}
