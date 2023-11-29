package app.pizzariatop.service;

import app.pizzariatop.convert.UsuarioDTOConvert;
import app.pizzariatop.dto.ItemDTO;
import app.pizzariatop.dto.PedidoDTO;
import app.pizzariatop.entity.Item;
import app.pizzariatop.entity.Pedido;
import app.pizzariatop.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioDTOConvert usuarioDTOConvert;

	@Autowired
	private ItemService itemService;

	public PedidoDTO criar(PedidoDTO pedidoDTO) {

		Pedido pedido = toPedido(pedidoDTO);

		if (pedido.getItem() == null) {
			System.out.println("lista nula");
		} else {
			System.out.println(pedido.getItem().size());
		}

		pedido.setStatus("PREPARO");

		pedidoRepository.save(pedido);

		return toPedidoDTO(pedido);
	}

	public PedidoDTO findById(Long id) {
		Pedido pedidoBanco = pedidoRepository.findById(id).orElse(null);

		PedidoDTO aux = toPedidoDTO(pedidoBanco);

		double valorTotal = 0;
		if (aux.getItem() != null)
			for (int j = 0; j < aux.getItem().size(); j++) {
				valorTotal += aux.getItem().get(j).getValor();
			}

		aux.setValorTotal(valorTotal);

		return aux;
	}

	public List<PedidoDTO> findAllPedido() {
		List<Pedido> pedidosBanco = pedidoRepository.findAll();
		List<PedidoDTO> pedidoDTOList = new ArrayList<>();

		for (int i = 0; i < pedidosBanco.size(); i++) {
			pedidoDTOList.add(toPedidoDTO(pedidosBanco.get(i)));

			double valorTotal = 0;
			if (pedidosBanco.get(i).getItem() != null)
				for (int j = 0; j < pedidosBanco.get(i).getItem().size(); j++) {
					valorTotal += pedidosBanco.get(i).getItem().get(j).getValor();
				}

			pedidoDTOList.get(i).setValorTotal(valorTotal);

		}

		return pedidoDTOList;
	}

	public PedidoDTO editar(Long id, PedidoDTO pedidoDTO) {
		Pedido pedido = this.pedidoRepository.findById(id).orElse(null);

		Assert.isTrue(pedido != null, "Pedido nao encontrado");

		Assert.isTrue(pedidoDTO.getNome() != null, "Sem nome");

		this.pedidoRepository.save(toPedido(pedidoDTO));

		return pedidoDTO;
	}

	public String deletar(Long id) {
		Pedido pedido = this.pedidoRepository.findById(id).orElse(null);

		Assert.isTrue(pedido != null, "Pedido nao encontrado");

		this.pedidoRepository.delete(pedido);

		return "Pedido deletado";
	}

//	public PedidoDTO finalizar(Long id) {
//		Pedido pedido = this.pedidoRepository.findById(id).orElse(null);
//
//		Assert.isTrue(pedido != null, "Pedido nao encontrado");
//
//
//	}

	public PedidoDTO toPedidoDTO(Pedido pedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();

		pedidoDTO.setId(pedido.getId());
		pedidoDTO.setNome(pedido.getNome());
		pedidoDTO.setObservacao(pedido.getObservacao());
		pedidoDTO.setEntrega(pedido.getEntrega());

		if (pedido.getUsuario() != null) {

			pedidoDTO.setUsuario(usuarioDTOConvert.convertUsuarioToUsuarioDTO(pedido.getUsuario()));
		}

		List<ItemDTO> itemsdump = new ArrayList<>();

		if (pedido.getItem() != null) {
			for (int i = 0; i < pedido.getItem().size(); i++) {
				itemsdump.add(itemService.toItemDTO(pedido.getItem().get(i)));
			}
		}

		pedidoDTO.setItem(itemsdump);
		return pedidoDTO;
	}

	public Pedido toPedido(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();

		pedido.setId(pedidoDTO.getId());
		pedido.setNome(pedidoDTO.getNome());
		pedido.setObservacao(pedidoDTO.getObservacao());
		pedido.setEntrega(pedidoDTO.getEntrega());

		if (pedidoDTO.getUsuario() != null) {

			pedido.setUsuario(usuarioDTOConvert.convertUsuarioDTOToUsuario(pedidoDTO.getUsuario()));
		}

		List<Item> itemList = new ArrayList<>();

		if (pedidoDTO.getItem() != null) {
			for (int i = 0; i < pedidoDTO.getItem().size(); i++) {
				itemList.add(itemService.toItem(pedidoDTO.getItem().get(i)));
			}
		}

		pedido.setItem(itemList);
		return pedido;
	}

}
