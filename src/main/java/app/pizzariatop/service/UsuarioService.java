package app.pizzariatop.service;

import app.pizzariatop.dto.UsuarioDTO;
import app.pizzariatop.convert.UsuarioDTOConvert;
import app.pizzariatop.entity.Usuario;
import app.pizzariatop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private  EnderecoService enderecoService;

    @Autowired
    private UsuarioDTOConvert usuarioDTOConvert;


    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public UsuarioDTO registrar(UsuarioDTO usuarioDTO) throws Exception{
        Usuario userEmailBanco = usuarioRepository.findByUsername(usuarioDTO.getUsername());
//        if(!userEmailBanco.getUsername().equals(usuarioDTO.getUsername())){
            Usuario userNew = new Usuario();
            userNew.setId(usuarioDTO.getId());
            userNew.setNome(usuarioDTO.getNome());
            userNew.setCpf(usuarioDTO.getCpf());
            userNew.setTelefone(usuarioDTO.getTelefone());
            userNew.setUsername(usuarioDTO.getUsername());
            userNew.setPassword(passwordEncoder().encode(usuarioDTO.getPassword()));
            userNew.setRole(usuarioDTO.getRole());

            usuarioRepository.save(userNew);

            return usuarioDTOConvert.convertUsuarioToUsuarioDTO(userNew);
//        }else {
//            throw new Exception("Usuario ja existente");
//        }
    }
    public List<UsuarioDTO> findByNome(String nome){
        List<Usuario> usuarioBanco = this.usuarioRepository.findPessoaByNome(nome);
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        for(int i = 0; i < usuarioBanco.size(); i++){
            usuarioDTOList.add(usuarioDTOConvert.convertUsuarioToUsuarioDTO(usuarioBanco.get(i)));
        }

        return usuarioDTOList;
    }


    public List<UsuarioDTO> findAllUsuarios(){
        List<Usuario> usuariosBanco = usuarioRepository.findAllUsuarios();
        List<UsuarioDTO> usuarioDTOList = new ArrayList<>();

        for(int i = 0; i < usuariosBanco.size(); i++){
            usuarioDTOList.add(usuarioDTOConvert.convertUsuarioToUsuarioDTO(usuariosBanco.get(i)));
        }

        return usuarioDTOList;
    }

    public UsuarioDTO editar(Long id,UsuarioDTO usuarioDTO){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);
        Usuario usuarioNew = usuarioDTOConvert.convertUsuarioDTOToUsuario(usuarioDTO);

        Assert.isTrue(usuarioBanco != null, "Usuario nao encontrado");

        usuarioRepository.save(usuarioNew);

        return usuarioDTOConvert.convertUsuarioToUsuarioDTO(usuarioNew);
    }

    public UsuarioDTO deletar(Long id){
        Usuario usuarioBanco = usuarioRepository.findById(id).orElse(null);

        Assert.isTrue(usuarioBanco != null, "Usuario nao encontrado");
        usuarioRepository.delete(usuarioBanco);

        return usuarioDTOConvert.convertUsuarioToUsuarioDTO(usuarioBanco);
    }


}
