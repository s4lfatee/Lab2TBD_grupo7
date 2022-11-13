package cl.tbd.voluntariadobetbd.repositories.Estado_tareaRepository;

import cl.tbd.voluntariadobetbd.models.Estado_tarea;

import java.util.List;

public interface Estado_tareaRepository {
    public List<Estado_tarea> getAll();
    public Estado_tarea getById(int id);
    public Estado_tarea post(Estado_tarea estado_tarea);
    public Estado_tarea put(int id, Estado_tarea estado_tarea);
    public int deleteAll();
    public int deleteById(int id);
}
