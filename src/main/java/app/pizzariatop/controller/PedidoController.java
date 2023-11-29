package app.pizzariatop.controller;

import app.pizzariatop.dto.PedidoDTO;
import app.pizzariatop.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
@CrossOrigin(origins = "*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody PedidoDTO pedidoDTO){
        try{
            return ResponseEntity.ok(pedidoService.criar(pedidoDTO));
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> buscarTodos(){
        try{
            return ResponseEntity.ok(pedidoService.findAllPedido());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<PedidoDTO> buscarId(@RequestParam("id")Long id){
        try{
            return ResponseEntity.ok(pedidoService.findById(id));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<PedidoDTO> editar(@PathVariable("id")Long id, @RequestBody PedidoDTO pedidoDTO){
        try{
            return ResponseEntity.ok(pedidoService.editar(id,pedidoDTO));
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deletar(@PathVariable("id") Long id){
        try{
            pedidoService.deletar(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
