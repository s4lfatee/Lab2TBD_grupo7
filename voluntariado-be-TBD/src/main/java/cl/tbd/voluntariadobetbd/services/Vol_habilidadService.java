package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Vol_habilidad;
import cl.tbd.voluntariadobetbd.repositories.Vol_habilidadRepository.Vol_habilidadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/vol_habilidad")
public class Vol_habilidadService {

    //no hay que cambiar nada mas alla que los nombres de con Control R
    private final Vol_habilidadRepository vol_habilidadRepository;
    Vol_habilidadService(Vol_habilidadRepository vol_habilidadRepository){
        this.vol_habilidadRepository = vol_habilidadRepository;
    }

    @GetMapping()
    public List<Vol_habilidad> getAll(){
        return this.vol_habilidadRepository.getAll();
    }

    @GetMapping("/{id}")
    public Vol_habilidad getById(@PathVariable("id") int id){
        return this.vol_habilidadRepository.getById(id);
    }


    @PostMapping()
    @ResponseBody
    public Vol_habilidad postById(@RequestBody Vol_habilidad vol_habilidad) {
        return this.vol_habilidadRepository.post(vol_habilidad);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Vol_habilidad putById(@PathVariable("id") int id, @RequestBody Vol_habilidad vol_habilidad ) {
        return this.vol_habilidadRepository.put(id, vol_habilidad);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.vol_habilidadRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every vol_habilidad was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.vol_habilidadRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the habilidad with id_habilidad equal to "
                    + Integer.toString(id)
                    + " was deleted");
        } else if (code == 0) {
            response.put("status", "409");
            response.put("error", "there arent a habilidads with id_habilidad equal to "
                    + Integer.toString(id));
        } else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}
