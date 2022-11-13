package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Eme_habilidad;
import cl.tbd.voluntariadobetbd.repositories.Eme_habilidadRepository.Eme_habilidadRepository;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

//usa control+R para cambiar altiro los nombres pero recuerda usar al "Cc" para cambiarlo apropiadamente

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/eme_habilidad")
public class Eme_habilidadService {

    //no hay que cambiar nada mas alla que los nombres de con Control R
    private final Eme_habilidadRepository eme_habilidadRepository;
    Eme_habilidadService(Eme_habilidadRepository eme_habilidadRepository){
        this.eme_habilidadRepository = eme_habilidadRepository;
    }

    @GetMapping()
    public List<Eme_habilidad> getAll(){
        return this.eme_habilidadRepository.getAll();
    }

    @GetMapping("/{id}")
    public Eme_habilidad getById(@PathVariable("id") int id){
        return this.eme_habilidadRepository.getById(id);
    }


    @PostMapping()
    @ResponseBody
    public Eme_habilidad postById(@RequestBody Eme_habilidad eme_habilidad) {
        return this.eme_habilidadRepository.post(eme_habilidad);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Eme_habilidad putById(@PathVariable("id") int id, @RequestBody Eme_habilidad eme_habilidad ) {
        return this.eme_habilidadRepository.put(id, eme_habilidad);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.eme_habilidadRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every eme_habilidad was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.eme_habilidadRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the eme_habilidad with id_eme_habilidad equal to "
                    + Integer.toString(id)
                    + " was deleted");
        } else if (code == 0) {
            response.put("status", "409");
            response.put("error", "there arent a eme_habilidads with id_eme_habilidad equal to "
                    + Integer.toString(id));
        } else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}