package cl.tbd.voluntariadobetbd.repositories.VoluntarioRepository;

import cl.tbd.voluntariadobetbd.models.Voluntario;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface VoluntarioRepository {
    public Voluntario getByName(String name);
    public Voluntario getById(int id);
    public List<Voluntario> getAll();
    public Voluntario post(Voluntario voluntario);
    public Voluntario put(int id, Voluntario voluntario);
    public int deleteById(int id);
    public int deleteAll();
    public List<Voluntario> getByMinScore(int id);
}
