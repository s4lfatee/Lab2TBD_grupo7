package cl.tbd.voluntariadobetbd.repositories.Tarea_habilidadRepository;

import java.util.List;
import cl.tbd.voluntariadobetbd.models.Tarea_habilidad;

public interface Tarea_habilidadRepository {
    public List<Tarea_habilidad> getAll();

    public Tarea_habilidad getById(int id);

    public Tarea_habilidad post(Tarea_habilidad Tarea_habilidad);

    public Tarea_habilidad put(int id, Tarea_habilidad Tarea_habilidad);

    public int deleteAll();

    public int deleteById(int id);
}