package app.pizzariatop.controller;

import app.pizzariatop.dto.UsuarioDTO;
import app.pizzariatop.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO usuarioDTO){
        try{
            return ResponseEntity.ok(usuarioService.registrar(usuarioDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UsuarioDTO>> buscarUsuarios(){
        try{
            return ResponseEntity.ok(usuarioService.findAllUsuarios());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> teste(){
        try{
            return ResponseEntity.ok(usuarioService.findAllUsuarios());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarNome(@RequestParam("nome")String nome){
        try{
            return ResponseEntity.ok(usuarioService.findByNome(nome));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<UsuarioDTO> editar(@PathVariable("id")Long id,@RequestBody UsuarioDTO  usuarioDTO){
        try{
           return ResponseEntity.ok(usuarioService.editar(id,usuarioDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<UsuarioDTO> deletar(@PathVariable("id")Long id){
        try{
           return ResponseEntity.ok(usuarioService.deletar(id));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
