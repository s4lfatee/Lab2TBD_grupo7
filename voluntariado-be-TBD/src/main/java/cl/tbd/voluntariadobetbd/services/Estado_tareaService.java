package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Estado_tarea;
import cl.tbd.voluntariadobetbd.repositories.Estado_tareaRepository.Estado_tareaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/estado_tarea")
public class Estado_tareaService {
    private final Estado_tareaRepository estado_tareaRepository;

    Estado_tareaService(Estado_tareaRepository estado_tareaRepository){ this.estado_tareaRepository = estado_tareaRepository; }

    @GetMapping()
    public List<Estado_tarea> getAll(){
        return this.estado_tareaRepository.getAll();
    }


    @PostMapping()
    @ResponseBody
    public Estado_tarea postById(@RequestBody Estado_tarea estado_tarea) {
        return this.estado_tareaRepository.post(estado_tarea);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Estado_tarea putById(@PathVariable("id") int id, @RequestBody Estado_tarea estado_tarea ) {
        return this.estado_tareaRepository.put(id, estado_tarea);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.estado_tareaRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every hability was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }
}
