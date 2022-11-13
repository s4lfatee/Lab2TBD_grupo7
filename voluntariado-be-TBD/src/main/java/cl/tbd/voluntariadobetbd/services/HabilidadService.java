package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Habilidad;
import cl.tbd.voluntariadobetbd.repositories.HabilidadRepository.HabilidadRepository;

import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

//usa control+R para cambiar altiro los nombres pero recuerda usar al "Cc" para cambiarlo apropiadamente

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/habilidad")
public class HabilidadService {

    //no hay que cambiar nada mas alla que los nombres de con Control R
    private final HabilidadRepository habilidadRepository;
    HabilidadService(HabilidadRepository habilidadRepository){
        this.habilidadRepository = habilidadRepository;
    }

    @GetMapping()
    public List<Habilidad> getAll(){
        return this.habilidadRepository.getAll();
    }

    @GetMapping("/{id}")
    public Habilidad getById(@PathVariable("id") int id){
        return this.habilidadRepository.getById(id);
    }


    @PostMapping()
    @ResponseBody
    public Habilidad postById(@RequestBody Habilidad habilidad) {
        return this.habilidadRepository.post(habilidad);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Habilidad putById(@PathVariable("id") int id, @RequestBody Habilidad habilidad ) {
        return this.habilidadRepository.put(id, habilidad);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.habilidadRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every habilidad was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.habilidadRepository.deleteById(id);
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