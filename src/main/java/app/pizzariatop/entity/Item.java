package app.pizzariatop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String tamanho;

    @ManyToMany(mappedBy = "item")
    private List<Pedido> pedido;

    private boolean possuiSabores;

    @ManyToMany
    @JoinTable(name = "item_sabores",
      joinColumns = @JoinColumn(name = "item_fk"),
      inverseJoinColumns = @JoinColumn(name = "sabores_fk"))
    private List<Sabores> sabores;
    
    private Float valor;
    	
    public Item(){

    }

    public Item(Long id, List<Pedido> pedido, String tamanho, Boolean entrega, List<Sabores> sabores) {
        this.id = id;
        //this.pedido = pedido;
        this.tamanho = tamanho;
        this.sabores = sabores;
    }

}
