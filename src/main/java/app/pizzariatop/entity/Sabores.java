package app.pizzariatop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Sabores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany(mappedBy = "sabores")
    private List<Item> item;

    public Sabores(){

    }

    public Sabores(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
