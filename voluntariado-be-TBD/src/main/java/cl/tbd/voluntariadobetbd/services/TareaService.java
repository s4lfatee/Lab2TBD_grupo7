package cl.tbd.voluntariadobetbd.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import cl.tbd.voluntariadobetbd.models.Tarea;
import cl.tbd.voluntariadobetbd.repositories.TareaRepository.TareaRepository;

@CrossOrigin
@RestController
@RequestMapping("/tarea")
public class TareaService {
    private final TareaRepository taskRepository;

    TareaService(TareaRepository tareaRepository){
        this.taskRepository = tareaRepository;
    }

    @GetMapping("/voluntario/{id}")
    public List<Tarea> getTasksByIdVolunteer(@PathVariable("id") int id_volunteer){
        return taskRepository.getTareasByIdVoluntario(id_volunteer);
    }

    @GetMapping()
    public List<Tarea> getAll(@RequestParam(required = false) Integer emergencia){
        if(emergencia != null){
            return taskRepository.getByIdEmergency(emergencia);
        }
        return this.taskRepository.getAllTareas();
    }

    @GetMapping("/{id}")
    public Tarea getById(@PathVariable("id") int id){
        return this.taskRepository.getTareaById(id);
    }


    @PostMapping()
    @ResponseBody
    public Tarea postById(@RequestBody Tarea institucion) {
        return this.taskRepository.post(institucion);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Tarea putById(@PathVariable("id") int id, @RequestBody Tarea institucion) {
        return this.taskRepository.put(id, institucion);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.taskRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every tasks was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.taskRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the task with id equal to "
                    + Integer.toString(id)
                    + " was deleted");
        }else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }

}
