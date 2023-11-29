package app.pizzariatop.service;

import app.pizzariatop.dto.ItemDTO;
import app.pizzariatop.dto.SaboresDTO;
import app.pizzariatop.entity.Item;
import app.pizzariatop.entity.Sabores;
import app.pizzariatop.repository.SaboresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaboresService {

    @Autowired
    private SaboresRepository saboresRepository;

    @Autowired
    private ItemService itemService;

    public SaboresDTO criar(SaboresDTO saboresDTO){
        Sabores sabores = toSabores(saboresDTO);

        this.saboresRepository.save(sabores);

        return toSaboresDTO(sabores);
    }

    public SaboresDTO findById(Long id){
        Sabores saboresBanco = saboresRepository.findById(id).orElse(null);

        return toSaboresDTO(saboresBanco);
    }

    public List<SaboresDTO> findAllSabores(){
        List<Sabores> saboresBanco = saboresRepository.findAll();
        List<SaboresDTO> saboresDTOList = new ArrayList<>();

        for(int i = 0; i < saboresBanco.size(); i++){
            saboresDTOList.add(toSaboresDTO(saboresBanco.get(i)));
        }

        return saboresDTOList;
    }

    public SaboresDTO editar(Long id, SaboresDTO saboresDTO){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);

        Assert.isTrue(sabores != null, "Sabor nao encontrado");

        this.saboresRepository.save(toSabores(saboresDTO));

        return saboresDTO;
    }

    public String deletar(Long id){
        Sabores sabores = this.saboresRepository.findById(id).orElse(null);

        Assert.isTrue(sabores != null, "Sabor nao encontrado");

        this.saboresRepository.delete(sabores);

        return "Sabor deletado";
    }

    public SaboresDTO toSaboresDTO(Sabores sabores){
        SaboresDTO saboresDTO = new SaboresDTO();

        saboresDTO.setNome(sabores.getNome());
        saboresDTO.setId(sabores.getId());

        List<ItemDTO> itemsDump = new ArrayList<>();

        if(sabores.getItem() != null){
            for(int i = 0; i < sabores.getItem().size(); i++){
                itemsDump.add(itemService.toItemDTO(sabores.getItem().get(i)));
            }
        }

        saboresDTO.setItemDTOS(itemsDump);
        return saboresDTO;
    }

    public Sabores toSabores(SaboresDTO saboresDTO){
        Sabores sabores = new Sabores();

        sabores.setNome(saboresDTO.getNome());
        sabores.setId(saboresDTO.getId());

        List<Item> itemsDump = new ArrayList<>();

        if(saboresDTO.getItemDTOS() != null){
            for(int i = 0; i < saboresDTO.getItemDTOS().size(); i++){
                itemsDump.add(itemService.toItem(saboresDTO.getItemDTOS().get(i)));
            }
        }

        sabores.setItem(itemsDump);
        return sabores;
    }

}
