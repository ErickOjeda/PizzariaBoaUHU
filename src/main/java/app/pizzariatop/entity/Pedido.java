package app.pizzariatop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "entrega")
    private Boolean entrega;
    
    private String status;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "item_pedidos",
      joinColumns = @JoinColumn(name = "pedido_fk"),
      inverseJoinColumns = @JoinColumn(name = "item_fk"))
    private List<Item> item;
    
    
    @ManyToOne
    @JoinColumn(name = "usuario_fk")
    private Usuario usuario;

    public Pedido(){

    }

    public Pedido(Long id, String nome, String observacao, Usuario usuario) {
        this.id = id;
        this.nome = nome;
        this.observacao = observacao;
        this.usuario = usuario;
    }
}
