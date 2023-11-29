package app.pizzariatop.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoDTO {

    private Long id;

    private String rua;

    private int numCasa;

    @JsonIgnoreProperties("enderecos")
    private UsuarioDTO usuario;

    public EnderecoDTO(){

    }

    public EnderecoDTO(Long id, String rua, int numCasa, UsuarioDTO usuarioDTO){
        this.id=id;
        this.rua=rua;
        this.numCasa=numCasa;
        this.usuario=usuarioDTO;
    }

}
