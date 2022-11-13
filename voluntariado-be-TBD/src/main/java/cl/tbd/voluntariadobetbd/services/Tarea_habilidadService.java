package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Tarea_habilidad;
import cl.tbd.voluntariadobetbd.repositories.Tarea_habilidadRepository.Tarea_habilidadRepository;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

//usa control+R para cambiar altiro los nombres pero recuerda usar al "Cc" para cambiarlo apropiadamente

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/tarea_habilidad")
public class Tarea_habilidadService {

    //no hay que cambiar nada mas alla que los nombres de con Control R
    private final Tarea_habilidadRepository tarea_habilidadRepository;
    Tarea_habilidadService(Tarea_habilidadRepository tarea_habilidadRepository){
        this.tarea_habilidadRepository = tarea_habilidadRepository;
    }

    @GetMapping()
    public List<Tarea_habilidad> getAll(){
        return this.tarea_habilidadRepository.getAll();
    }

    @GetMapping("/{id}")
    public Tarea_habilidad getById(@PathVariable("id") int id){
        return this.tarea_habilidadRepository.getById(id);
    }


    @PostMapping()
    @ResponseBody
    public Tarea_habilidad postById(@RequestBody Tarea_habilidad tarea_habilidad) {
        return this.tarea_habilidadRepository.post(tarea_habilidad);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Tarea_habilidad putById(@PathVariable("id") int id, @RequestBody Tarea_habilidad tarea_habilidad ) {
        return this.tarea_habilidadRepository.put(id, tarea_habilidad);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.tarea_habilidadRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every tarea_habilidad was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.tarea_habilidadRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the tarea_habilidad with id_tarea_habilidad equal to "
                    + Integer.toString(id)
                    + " was deleted");
        } else if (code == 0) {
            response.put("status", "409");
            response.put("error", "there arent a tarea_habilidads with id_tarea_habilidad equal to "
                    + Integer.toString(id));
        } else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}