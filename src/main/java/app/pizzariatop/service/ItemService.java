package app.pizzariatop.service;

import app.pizzariatop.convert.UsuarioDTOConvert;
import app.pizzariatop.dto.ItemDTO;
import app.pizzariatop.dto.PedidoDTO;
import app.pizzariatop.dto.SaboresDTO;
import app.pizzariatop.entity.Item;
import app.pizzariatop.entity.Pedido;
import app.pizzariatop.entity.Sabores;
import app.pizzariatop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private UsuarioDTOConvert usuarioDTOConvert;

//    @Autowired
//    private PedidoService pedidoService;

	public ItemDTO criar(ItemDTO itemDTO) {

		System.out.print(itemDTO.getTamanho());

		if (itemDTO.getSabores() != null) {
			System.out.print(itemDTO.getSabores().size());

			if (itemDTO.getTamanho().equals("Pequena") && itemDTO.getSabores().size() > 1) {
				throw new RuntimeException("O Tamanho 'Pequeno', so pode ter 1 sabor");
			} else if (itemDTO.getTamanho().equals("Media") && itemDTO.getSabores().size() > 2) {
				throw new RuntimeException("O Tamanho 'Media', so pode ter 2 sabor");
			} else if (itemDTO.getTamanho().equals("Grande") && itemDTO.getSabores().size() > 3) {
				throw new RuntimeException("O Tamanho 'Grande', so pode ter 3 sabor");
			}
		}

		Item item = toItem(itemDTO);

		itemRepository.save(item);

		return toItemDTO(item);
	}

	public ItemDTO findById(Long id) {
		Item itemBanco = itemRepository.findById(id).orElse(null);

		return toItemDTO(itemBanco);
	}

	public List<ItemDTO> findAllItens() {
		List<Item> itensBanco = itemRepository.findAll();
		List<ItemDTO> itensDTOList = new ArrayList<>();

		for (int i = 0; i < itensBanco.size(); i++) {
			itensDTOList.add(toItemDTO(itensBanco.get(i)));
		}
		return itensDTOList;
	}

	public ItemDTO editar(Long id, ItemDTO itemDTO) {
		Item item = this.itemRepository.findById(id).orElse(null);

		Assert.isTrue(item != null, "Item nao encontrado");

		this.itemRepository.save(toItem(itemDTO));

		return itemDTO;
	}

	public String deletar(Long id) {
		Item item = this.itemRepository.findById(id).orElse(null);

		Assert.isTrue(item != null, "Item nao encontrado");

		this.itemRepository.delete(item);

		return "Item deletado";
	}

	public ItemDTO toItemDTO(Item item) {
		ItemDTO itemDTO = new ItemDTO();

		itemDTO.setId(item.getId());
		itemDTO.setNome(item.getNome());
		itemDTO.setTamanho(item.getTamanho());
		itemDTO.setPossuiSabores(item.isPossuiSabores());
		itemDTO.setValor(item.getValor());
		// itemDTO.setPedidoDTO(pedidoService.toPedidoDTO(item.getPedido()));

		List<SaboresDTO> saboresList = new ArrayList<>();

		if (item.getSabores() != null) {
			for (int i = 0; i < item.getSabores().size(); i++) {
				saboresList.add(toSaboresDTO(item.getSabores().get(i)));
			}
		}

		itemDTO.setSabores(saboresList);
		return itemDTO;
	}

	public Item toItem(ItemDTO itemDTO) {
		Item item = new Item();

		item.setId(itemDTO.getId());
		item.setNome(itemDTO.getNome());
		item.setTamanho(itemDTO.getTamanho());
		item.setPossuiSabores(itemDTO.isPossuiSabores());
		// item.setPedido(pedidoService.toPedido(itemDTO.getPedidoDTO()));
		item.setValor(itemDTO.getValor());

		List<Sabores> saboresList = new ArrayList<>();

		if (itemDTO.getSabores() != null) {
			for (int i = 0; i < itemDTO.getSabores().size(); i++) {
				saboresList.add(toSabores(itemDTO.getSabores().get(i)));
			}
		}

		item.setSabores(saboresList);
		return item;
	}

	// FUNCOES DE TOPEDIDO E TOPEDIDODTO
	public Pedido toPedido(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();

		pedido.setId(pedidoDTO.getId());
		pedido.setNome(pedidoDTO.getNome());
		pedido.setObservacao(pedidoDTO.getObservacao());
		pedido.setUsuario(usuarioDTOConvert.convertUsuarioDTOToUsuario(pedidoDTO.getUsuario()));

		List<Item> itemList = new ArrayList<>();

		if (pedidoDTO.getItem() != null) {
			for (int i = 0; i < pedidoDTO.getItem().size(); i++) {
				itemList.add(toItem(pedidoDTO.getItem().get(i)));
			}
		}

		pedido.setItem(itemList);
		return pedido;
	}

	public PedidoDTO toPedidoDTO(Pedido pedido) {
		PedidoDTO pedidoDTO = new PedidoDTO();

		pedidoDTO.setId(pedido.getId());
		pedidoDTO.setNome(pedido.getNome());
		pedidoDTO.setObservacao(pedido.getObservacao());
		pedidoDTO.setUsuario(usuarioDTOConvert.convertUsuarioToUsuarioDTO(pedido.getUsuario()));

		List<ItemDTO> itemsdump = new ArrayList<>();

		if (pedido.getItem() != null) {
			for (int i = 0; i < pedido.getItem().size(); i++) {
				itemsdump.add(toItemDTO(pedido.getItem().get(i)));
			}
		}

		pedidoDTO.setItem(itemsdump);
		return pedidoDTO;
	}

	public SaboresDTO toSaboresDTO(Sabores sabores) {
		SaboresDTO saboresDTO = new SaboresDTO();

		saboresDTO.setNome(sabores.getNome());
		saboresDTO.setId(sabores.getId());

		return saboresDTO;
	}

	public Sabores toSabores(SaboresDTO saboresDTO) {
		Sabores sabores = new Sabores();

		sabores.setNome(saboresDTO.getNome());
		sabores.setId(saboresDTO.getId());

		return sabores;
	}
}
