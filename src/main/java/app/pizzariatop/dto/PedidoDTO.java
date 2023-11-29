package app.pizzariatop.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PedidoDTO {
	private Long id;

	private String nome;

	private String observacao;

	private UsuarioDTO usuario;

	private Boolean entrega;

	private String status;

	private double valorTotal;

	private List<ItemDTO> item;

	public PedidoDTO() {

	}

	public PedidoDTO(Long id, String nome, String observacao, UsuarioDTO usuarioDTO) {
		this.id = id;
		this.nome = nome;
		this.observacao = observacao;
		this.usuario = usuarioDTO;
	}
}
