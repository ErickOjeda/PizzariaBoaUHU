package app.pizzariatop.service;

import app.pizzariatop.dto.EnderecoDTO;
import app.pizzariatop.dto.UsuarioDTO;
import app.pizzariatop.entity.Endereco;
import app.pizzariatop.entity.Usuario;
import app.pizzariatop.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;


    public EnderecoDTO criar(EnderecoDTO enderecoDTO){
        Endereco endereco = toEndereco(enderecoDTO);

        this.enderecoRepository.save(endereco);

        return toEnderecoDTO(endereco);
    }

    public List<EnderecoDTO> findAllEnderecos(){
        List<Endereco> enderecosBanco = enderecoRepository.findAll();
        List<EnderecoDTO> enderecoDTOList = new ArrayList<>();

        for(int i = 0; i < enderecosBanco.size(); i++){
            enderecoDTOList.add(toEnderecoDTO(enderecosBanco.get(i)));
        }

        return enderecoDTOList;
    }

    public EnderecoDTO editar(Long id,EnderecoDTO enderecoDTO){

        Endereco enderecoBanco = this.enderecoRepository.findById(id).orElse(null);

        Assert.isTrue(enderecoBanco != null, "Endereco nao encontrado");

        this.enderecoRepository.save(toEndereco(enderecoDTO));

        return enderecoDTO;
    }

    public String deletar(Long id){
        Endereco enderecoBanco = this.enderecoRepository.findById(id).orElse(null);

        Assert.isTrue(enderecoBanco != null, "Endereco nao encontrado");
        this.enderecoRepository.delete(enderecoBanco);
        return null;
    }

    public EnderecoDTO toEnderecoDTO(Endereco endereco){
        EnderecoDTO enderecoDTO1 = new EnderecoDTO();

        enderecoDTO1.setId(endereco.getId());
        enderecoDTO1.setRua(endereco.getRua());
        enderecoDTO1.setNumCasa(endereco.getNumCasa());
        enderecoDTO1.setUsuario(toUsuarioDTO(endereco.getUsuario()));

        return enderecoDTO1;
    }

    public Endereco toEndereco(EnderecoDTO enderecoDTO){
        Endereco endereco = new Endereco();
        endereco.setId(enderecoDTO.getId());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumCasa(enderecoDTO.getNumCasa());
        endereco.setUsuario(toUsuario(enderecoDTO.getUsuario()));
        return endereco;
    }


    public Usuario toUsuario(UsuarioDTO usuarioDTO){

        Usuario usuario = new Usuario();

        usuario.setId(usuarioDTO.getId());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setTelefone(usuarioDTO.getTelefone());

        List<Endereco> dump = new ArrayList<>();

//        if(usuarioDTO.getEnderecos() != null){
//            for(int i = 0; i < usuarioDTO.getEnderecos().size(); i++){
//                Endereco aux = toEndereco(usuarioDTO.getEnderecos().get(i));
//                aux.setUsuario(usuario);
//                dump.add(aux);
//            }
//        }
//        usuario.setEnderecos(dump);

        return usuario;
    }

    public UsuarioDTO toUsuarioDTO(Usuario usuario){

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setTelefone(usuario.getTelefone());
/*
    if(!enderecoJaConvertido) {
        List<EnderecoDTO> dump = new ArrayList<>();

        if (usuario.getEnderecos() != null) {
            for (int i = 0; i < usuario.getEnderecos().size(); i++) {
                dump.add(toEnderecoDTO(usuario.getEnderecos().get(i)));
            }
        }
        usuarioDTO.setEnderecos(dump);
    }*/
        return usuarioDTO;
    }
}
