package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Emergencia;
import cl.tbd.voluntariadobetbd.repositories.EmergenciaRepository.EmergenciaRepository;

import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

//usa control+R para cambiar altiro los nombres pero recuerda usar al "Cc" para cambiarlo apropiadamente

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/emergencia")
public class EmergenciaService {

    //no hay que cambiar nada mas alla que los nombres de con Control R
    private final EmergenciaRepository emergenciaRepository;
    EmergenciaService(EmergenciaRepository emergenciaRepository){
        this.emergenciaRepository = emergenciaRepository;
    }

    @GetMapping("/nearest/{id}")
    public List<Emergencia> getNearBy(@PathVariable("id") int id){
        return emergenciaRepository.getNearBy(id);
    }

    @GetMapping()
    public List<Emergencia> getAll(@RequestParam(required = false) Date inicio, @RequestParam(required = false) Date fin){
        if(inicio == null || fin == null){
            return this.emergenciaRepository.getAll();
        }
        return emergenciaRepository.getBetweenDates(inicio, fin);
    }

    @GetMapping("/{id}")
    public Emergencia getById(@PathVariable("id") int id){
        return this.emergenciaRepository.getById(id);
    }


    @PostMapping()
    @ResponseBody
    public Emergencia postById(@RequestBody Emergencia emergencia) {
        return this.emergenciaRepository.post(emergencia);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Emergencia putById(@PathVariable("id") int id, @RequestBody Emergencia emergencia ) {
        return this.emergenciaRepository.put(id, emergencia);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.emergenciaRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every emergencia was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.emergenciaRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the emergencia with id_emergencia equal to "
                    + Integer.toString(id)
                    + " was deleted");
        } else if (code == 0) {
            response.put("status", "409");
            response.put("error", "there arent a emergencias with id_emergencia equal to "
                    + Integer.toString(id));
        } else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}