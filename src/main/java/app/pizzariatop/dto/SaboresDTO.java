package app.pizzariatop.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaboresDTO {

    private Long id;

    private String nome;

    private List<ItemDTO> itemDTOS;

    public SaboresDTO(){

    }

    public SaboresDTO(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
