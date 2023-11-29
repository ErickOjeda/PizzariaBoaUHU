package app.pizzariatop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "num_casa")
    private int numCasa;


    @JsonIgnoreProperties("enderecos")
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Endereco(){
        //Necessario para criar uma instancia.
    }

    public Endereco(Long id, String rua, int numCasa, Usuario usuario) {
        this.id = id;
        this.rua = rua;
        this.numCasa = numCasa;
        this.usuario = usuario;
    }
}
