package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Institucion;
import cl.tbd.voluntariadobetbd.repositories.InstitucionRepository.InstitucionRepository;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/institucion")
public class InstitucionService {

    private final InstitucionRepository institucionRepository;
    InstitucionService(InstitucionRepository institucionRepository){
        this.institucionRepository = institucionRepository;
    }

    @GetMapping()
    public List<Institucion> getAll(){
        return this.institucionRepository.getAll();
    }

    @GetMapping("/{id}")
    public Institucion getById(@PathVariable("id") int id){
        return this.institucionRepository.getById(id);
    }


    @PostMapping()
    @ResponseBody
    public Institucion postById(@RequestBody Institucion institucion) {
        return this.institucionRepository.post(institucion);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Institucion putById(@PathVariable("id") int id, @RequestBody Institucion institucion) {
        return this.institucionRepository.put(id, institucion);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.institucionRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every institution was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.institucionRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the institution with id equal to "
                    + Integer.toString(id)
                    + " was deleted");
        }else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}
