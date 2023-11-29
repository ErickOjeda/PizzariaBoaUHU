package app.pizzariatop.controller;

import app.pizzariatop.dto.EnderecoDTO;
import app.pizzariatop.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/endereco")
@CrossOrigin(origins = "*")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;


    @PostMapping
    public ResponseEntity<EnderecoDTO> criar(@RequestBody final EnderecoDTO enderecoDTO){
        try{
            return  ResponseEntity.ok( enderecoService.criar(enderecoDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<EnderecoDTO>> buscarEnderecos(){
        try{
           return ResponseEntity.ok(enderecoService.findAllEnderecos());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<EnderecoDTO> editar(@PathVariable("id") Long id,@RequestBody EnderecoDTO enderecoDTO){
        try{
            return ResponseEntity.ok( enderecoService.editar(id,enderecoDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletar(@PathVariable("id")Long id){
        try{
            enderecoService.deletar(id);
            return ResponseEntity.ok("Endereco deletado com sucesso");
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
