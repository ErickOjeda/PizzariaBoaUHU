package app.pizzariatop.teste;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import app.pizzariatop.controller.EnderecoController;
import app.pizzariatop.controller.ItemController;
import app.pizzariatop.controller.LoginController;
import app.pizzariatop.controller.PedidoController;
import app.pizzariatop.controller.SaboresController;
import app.pizzariatop.controller.UsuarioController;
import app.pizzariatop.dto.EnderecoDTO;
import app.pizzariatop.dto.ItemDTO;
import app.pizzariatop.dto.LoginDTO;
import app.pizzariatop.dto.PedidoDTO;
import app.pizzariatop.dto.SaboresDTO;
import app.pizzariatop.dto.UsuarioDTO;
import app.pizzariatop.entity.Endereco;
import app.pizzariatop.entity.Item;
import app.pizzariatop.entity.Pedido;
import app.pizzariatop.entity.Sabores;
import app.pizzariatop.entity.Usuario;
import app.pizzariatop.repository.EnderecoRepository;
import app.pizzariatop.repository.ItemRepository;
import app.pizzariatop.repository.LoginRepository;
import app.pizzariatop.repository.PedidoRepository;
import app.pizzariatop.repository.SaboresRepository;
import app.pizzariatop.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class PizzariaApplicationTests {


	@MockBean
	UsuarioRepository usuarioRepository;

	@MockBean
	PedidoRepository pedidoRepository;

	@MockBean
	ItemRepository itemRepository;

	@MockBean
	SaboresRepository saboresRepository;

	@MockBean
	EnderecoRepository enderecoRepository;

	@MockBean
	LoginRepository loginRepository;

	@Autowired
	private UsuarioController usuarioController;

	@Autowired
	private PedidoController pedidoController;

	@Autowired
	private ItemController itemController;

	@Autowired
	private SaboresController saboresController;

	@Autowired
	private EnderecoController enderecoController;

	@Autowired
	private LoginController loginController;

	@BeforeEach
	void injectData(){
		Usuario usuario = new Usuario(1L,"Andre","123123123","800.123.123-22");
		Usuario usuario1 = new Usuario(1L,"Carlos","123","800");
		List<Usuario> usuarioList= new ArrayList<>();
		usuarioList.add(usuario);
		usuarioList.add(usuario1);

		Endereco endereco = new Endereco(1L, "Av.Brasil",123,usuario);
		List<Endereco> enderecoList = new ArrayList<>();
		enderecoList.add(endereco);

		Pedido pedido = new Pedido(1L,"Pizza","Nenhuma observacao",usuario);
		List<Pedido> pedidoList = new ArrayList<>();
		pedidoList.add(pedido);

		Sabores sabores = new Sabores(1L, "Calabresa");
		List<Sabores> saboresList = new ArrayList<>();
		saboresList.add(sabores);

		Item item = new Item(1L,pedidoList,"Grande",true,saboresList);
		List<Item> itemList = new ArrayList<>();
		itemList.add(item);

		Login login = new Login(1L, "exemplo@exemplo.com", "senha", usuario);
		List<Login> loginList = new ArrayList<>();
		loginList.add(login);

		Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario);
		Mockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
		Mockito.when(usuarioRepository.findPessoaByNome("Andre")).thenReturn(usuarioList);
		Mockito.when(usuarioRepository.findAllUsuarios()).thenReturn(usuarioList);

		Mockito.when(pedidoRepository.save(pedido)).thenReturn(pedido);
		Mockito.when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
		Mockito.when(pedidoRepository.findAll()).thenReturn(pedidoList);

		Mockito.when(itemRepository.save(item)).thenReturn(item);
		Mockito.when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
		Mockito.when(itemRepository.findAll()).thenReturn(itemList);

		Mockito.when(saboresRepository.save(sabores)).thenReturn(sabores);
		Mockito.when(saboresRepository.findById(1L)).thenReturn(Optional.of(sabores));
		Mockito.when(saboresRepository.findAll()).thenReturn(saboresList);

		Mockito.when(enderecoRepository.save(endereco)).thenReturn(endereco);
		Mockito.when(enderecoRepository.findById(1L)).thenReturn(Optional.of(endereco));
		Mockito.when(enderecoRepository.findAll()).thenReturn(enderecoList);

		Mockito.when(loginRepository.save(login)).thenReturn(login);
		Mockito.when(loginRepository.findById(1L)).thenReturn(Optional.of(login));
		Mockito.when(loginRepository.findAll()).thenReturn(loginList);
	}

	//-----------------------USUARIO----------------------------//

	@Test
	void criarUsuario() {
		var data = usuarioController.criar(new UsuarioDTO(1L,"Andre","123123123","800.123.123-22"));

		Assert.assertEquals("Andre", data.getBody().getNome());

	}

	@Test
	void buscarUsuarioByNome(){
		var data = usuarioController.buscarNome("Andre");

		Assert.assertEquals("Andre", data.getBody().get(0).getNome());
	}

	@Test
	void buscarTodosUsuarios(){
		var data = usuarioController.buscarUsuarios();

		Assert.assertEquals("Carlos", data.getBody().get(1).getNome());
	}

	@Test
	void editarUsuario(){
		var data = usuarioController.editar(1L,new UsuarioDTO(1L,"Carlos","123123123","800.123.123-22"));

		Assert.assertEquals("Carlos",data.getBody().getNome());
	}

	@Test
	void deletarUsuario(){
		var data = usuarioController.deletar(1L);

		Assert.assertEquals("usuario deletado",data.getBody());
	}

	//--------------------------PEDIDO------------------------------------//

	@Test
	void criarPedido(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L,"Andre","123123123","800.123.123-22");
		PedidoDTO pedidoDTO = new PedidoDTO(1L,"Pizza","Nenhuma observacao",usuarioDTO);

		var data = pedidoController.criar(pedidoDTO);

		Assert.assertEquals("Pizza", data.getBody().getNome());
	}

	@Test
	void buscarPedido(){
		var data = pedidoController.buscarId(1L);

		Assert.assertEquals(1L, data.getBody().getId().longValue());
	}

	@Test
	void buscarTodosPedidos(){
		var data = pedidoController.buscarTodos();

		Assert.assertEquals("Pizza", data.getBody().get(0).getNome());
	}

	@Test
	void editarPedido(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L,"Andre","123123123","800.123.123-22");
		PedidoDTO pedidoDTO = new PedidoDTO(1L,"Hamburguer","Nenhuma observacao",usuarioDTO);

		var data = pedidoController.editar(1L, pedidoDTO);

		Assert.assertEquals("Hamburguer",data.getBody().getNome());
	}

	@Test
	void deletarPedido(){
		var data = pedidoController.deletar(1L);

		Assert.assertEquals("Pedido deletado", data.getBody());
	}

	//--------------------ITEM-------------------------//

	@Test
	void criarItem(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L,"Andre","123123123","800.123.123-22");
		PedidoDTO pedidoDTO = new PedidoDTO(1L,"Pizza","Nenhuma observacao",usuarioDTO);
		List<PedidoDTO> pedidoDTOList = new ArrayList<>();
		pedidoDTOList.add(pedidoDTO);
		SaboresDTO saboresDTO = new SaboresDTO(1L, "Calabresa");

		List<SaboresDTO> saboresDTOList = new ArrayList<>();
		saboresDTOList.add(saboresDTO);

		ItemDTO itemDTO = new ItemDTO(1L,pedidoDTOList,"Grande",true,saboresDTOList);

		var data = itemController.criar(itemDTO);

		Assert.assertEquals("Grande",data.getBody().getTamanho());
	}

	@Test
	void editarItem(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L,"Andre","123123123","800.123.123-22");
		PedidoDTO pedidoDTO = new PedidoDTO(1L,"Pizza","Nenhuma observacao",usuarioDTO);
		List<PedidoDTO> pedidoDTOList = new ArrayList<>();
		pedidoDTOList.add(pedidoDTO);
		SaboresDTO saboresDTO = new SaboresDTO(1L, "Calabresa");

		List<SaboresDTO> saboresDTOList = new ArrayList<>();
		saboresDTOList.add(saboresDTO);

		ItemDTO itemDTO = new ItemDTO(1L,pedidoDTOList,"Pequeno",true,saboresDTOList);

		var data = itemController.editar(1L,itemDTO);

		Assert.assertEquals("Pequeno",data.getBody().getTamanho());
	}

	@Test
	void buscarItem(){
		var data = itemController.buscarId(1L);

		Assert.assertEquals(1L, data.getBody().getId().longValue());
	}

	@Test
	void buscarTodosItens(){
		var data = itemController.buscarTodos();

		Assert.assertEquals("Grande", data.getBody().get(0).getTamanho());
	}

	@Test
	void deletarItem(){
		var data = itemController.deletar(1L);

		Assert.assertEquals("Item deletado", data.getBody());
	}

	//-------------------SABORES-------------------//

	@Test
	void criarSabores(){
		SaboresDTO saboresDTO = new SaboresDTO(1L, "Calabresa");

		var data = saboresController.criar(saboresDTO);

		Assert.assertEquals("Calabresa",data.getBody().getNome());

	}

	@Test
	void buscarSabor(){
		var data = saboresController.buscarId(1L);

		Assert.assertEquals(1L, data.getBody().getId().longValue());
	}

	@Test
	void buscarTodosSabores(){
		var data = saboresController.buscarTodos();

		Assert.assertEquals("Calabresa", data.getBody().get(0).getNome());
	}

	@Test
	void editarSabores(){
		SaboresDTO saboresDTO = new SaboresDTO(1L, "Portuguesa");

		var data = saboresController.editar(1L,saboresDTO);

		Assert.assertEquals("Portuguesa",data.getBody().getNome());
	}

	@Test
	void deletarSabores(){
		var data = saboresController.deletar(1L);

		Assert.assertEquals("Sabor deletado",data.getBody());
	}

	//------------------Endereco---------------------//

	@Test
	void criarEndereco(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L,"Andre","123123123","800.123.123-22");

		EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "Av.Brasil",123,usuarioDTO);


		var createdEnderecoDTO = enderecoController.criar(enderecoDTO);

		Assert.assertEquals(enderecoDTO.getId(), createdEnderecoDTO.getBody().getId());
		Assert.assertEquals(enderecoDTO.getRua(), createdEnderecoDTO.getBody().getRua());
		Assert.assertEquals(enderecoDTO.getNumCasa(), createdEnderecoDTO.getBody().getNumCasa());
	}

	@Test
	void editarEndereco(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Andre", "123123123", "800.123.123-22");
		EnderecoDTO enderecoDTO = new EnderecoDTO(1L, "Av.Brasil", 123,usuarioDTO);
		enderecoDTO.setUsuario(usuarioDTO);

		var endereco = enderecoController.editar(1L,enderecoDTO);

		Assert.assertEquals(enderecoDTO.getRua(), endereco.getBody().getRua());
		Assert.assertEquals(enderecoDTO.getNumCasa(), endereco.getBody().getNumCasa());
	}

	@Test
	void buscarTodosEnderecos(){
		var data = enderecoController.buscarEnderecos();

		Assert.assertEquals("Av.Brasil", data.getBody().get(0).getRua());
	}

	@Test
	void deletarEndereco(){
		var result = enderecoController.deletar(1L);

		Assert.assertEquals("Endereco deletado com sucesso", result.getBody());
	}

	//--------------------Login-------------------//

	@Test
	void criarLogin(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Andre", "123123123", "800.123.123-22");
		LoginDTO loginDTO = new LoginDTO(1L, "exemplo@exemplo.com", "senha",usuarioDTO);

		var createdLogin = loginController.criar(loginDTO);

		Assert.assertEquals(loginDTO.getEmail(), createdLogin.getBody().getEmail());
		Assert.assertEquals(loginDTO.getSenha(), createdLogin.getBody().getSenha());
	}

	@Test
	void editarLogin(){
		UsuarioDTO usuarioDTO = new UsuarioDTO(1L, "Andre", "123123123", "800.123.123-22");
		LoginDTO loginDTO = new LoginDTO(1L, "exemplo@exemplo.com", "admin",usuarioDTO);

		var Login = loginController.editar(1L, loginDTO);

		Assert.assertEquals("exemplo@exemplo.com", Login.getBody().getEmail());
		Assert.assertEquals("admin", Login.getBody().getSenha());

	}

	@Test
	void buscarTodosLogins(){
		var data = loginController.buscarTodos();

		Assert.assertEquals("exemplo@exemplo.com", data.getBody().get(0).getEmail());
	}

	@Test
	void deletarLogin(){
		var result = loginController.deletar(1L);
		Assert.assertEquals("Login deletado com sucesso", result.getBody());
	}

}
