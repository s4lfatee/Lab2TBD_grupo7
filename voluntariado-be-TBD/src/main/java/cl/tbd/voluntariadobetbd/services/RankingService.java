package cl.tbd.voluntariadobetbd.services;

import cl.tbd.voluntariadobetbd.models.Ranking;
import cl.tbd.voluntariadobetbd.repositories.RankingRepository.RankingRepository;

import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

//usa control+R para cambiar altiro los nombres pero recuerda usar al "Cc" para cambiarlo apropiadamente

@CrossOrigin
@RestController
//cambiar el nombre de aca con el nombre de la tabla
@RequestMapping("/ranking")
public class RankingService {

    //no hay que cambiar nada mas alla que los nombres de con Control R
    private final RankingRepository rankingRepository;
    RankingService(RankingRepository rankingRepository){
        this.rankingRepository = rankingRepository;
    }

    @GetMapping()
    public List<Ranking> getAll(){
        return this.rankingRepository.getAll();
    }

    @PatchMapping()
    public int updateStateTarea(@RequestParam Integer id_tarea, @RequestParam Integer id_voluntario, @RequestParam Integer nuevo_estado){
        return rankingRepository.updateStateTarea(id_voluntario,id_tarea,nuevo_estado);
    }

    @PostMapping()
    @ResponseBody
    public Ranking postById(@RequestBody Ranking ranking) {
        return this.rankingRepository.post(ranking);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Ranking putById(@PathVariable("id") int id, @RequestBody Ranking ranking ) {
        return this.rankingRepository.put(id, ranking);
    }

    @DeleteMapping()
    public Map<String, String> deleteAll(){
        int code = this.rankingRepository.deleteAll();
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "every ranking was deleted");
        }else{
            response.put("status", "400");
            response.put("error", "something went wrong");
        }

        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteById(@PathVariable("id") int id){
        int code = this.rankingRepository.deleteById(id);
        HashMap<String, String> response = new HashMap<>();
        if(code == 1){
            response.put("status", "200");
            response.put("response", "the ranking with id_ranking equal to "
                    + Integer.toString(id)
                    + " was deleted");
        }else{
            response.put("status", "400");
            response.put("response", "something went wrong");
        }

        return response;
    }
}