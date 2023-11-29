package app.pizzariatop.dto;

import java.time.LocalDate;

import app.pizzariatop.entity.Pedido;

public class ReciboDTO {
	
	private Long id;
	
	private Double valorTotal;
	
	private Pedido pedido;
	
	private LocalDate dataHora;
	
	public ReciboDTO(Long id, Double valorTotal, Pedido pedido, LocalDate dataHora) {
		super();
		this.id = id;
		this.valorTotal = valorTotal;
		this.pedido = pedido;
		this.dataHora = dataHora;
	}
	
	public ReciboDTO() {
		
	}

}
