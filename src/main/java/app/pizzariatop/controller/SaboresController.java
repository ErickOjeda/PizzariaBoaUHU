package app.pizzariatop.controller;

import app.pizzariatop.dto.SaboresDTO;
import app.pizzariatop.service.SaboresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/sabores")
@CrossOrigin(origins = "*")
public class SaboresController {

    @Autowired
    private SaboresService saboresService;

    @PostMapping
    public ResponseEntity<SaboresDTO> criar (@RequestBody SaboresDTO saboresDTO){
        try{
            return ResponseEntity.ok(saboresService.criar(saboresDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<SaboresDTO>> buscarTodos(){
        try{
            return ResponseEntity.ok(saboresService.findAllSabores());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/buscar/{id}")
    public  ResponseEntity<SaboresDTO> buscarId(@RequestParam("id")Long id){
        try{
            return ResponseEntity.ok(saboresService.findById(id));
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<SaboresDTO> editar(@PathVariable("id")Long id, @RequestBody SaboresDTO saboresDTO){
        try{
            return ResponseEntity.ok(saboresService.editar(id,saboresDTO));
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deletar(@PathVariable("id") Long id){
        try{
            saboresService.deletar(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
