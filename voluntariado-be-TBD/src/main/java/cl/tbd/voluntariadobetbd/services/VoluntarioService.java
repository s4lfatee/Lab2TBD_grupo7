package cl.tbd.voluntariadobetbd.services;

import org.springframework.web.bind.annotation.*;

import cl.tbd.voluntariadobetbd.models.Voluntario;
import cl.tbd.voluntariadobetbd.repositories.VoluntarioRepository.VoluntarioRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/voluntario")
public class VoluntarioService {
    private final VoluntarioRepository voluntarioRepository;
    VoluntarioService(VoluntarioRepository voluntarioRepository){
        this.voluntarioRepository = voluntarioRepository;
    }

    @GetMapping("/name/{name}")
    public Voluntario getByName(@PathVariable("name") String name){
        return voluntarioRepository.getByName(name);
    }
    @GetMapping("/{id}")
    public Voluntario getById(@PathVariable("id") int id){
        return voluntarioRepository.getById(id);
    }

    @GetMapping("/min/{idTask}")
    public List<Voluntario> getByMinScoreByIdTask(@PathVariable("idTask") int id){
        return voluntarioRepository.getByMinScore(id);
    }
    @GetMapping()
    public List<Voluntario> getAll(){
        return voluntarioRepository.getAll();
    }

    @PostMapping()
    public Voluntario post(@RequestBody Voluntario voluntario){
        return voluntarioRepository.post(voluntario);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Voluntario put(@PathVariable("id") int id, @RequestBody Voluntario voluntario ) {
        return voluntarioRepository.put(id, voluntario);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.voluntarioRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every volunteer was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.voluntarioRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the volunteer with id equal to "
                    + id
                    + " was deleted");
        }else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}
