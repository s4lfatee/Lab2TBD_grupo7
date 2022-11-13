package cl.tbd.voluntariadobetbd.repositories.TareaRepository;

import java.sql.Date;
import java.util.List;

import cl.tbd.voluntariadobetbd.models.Tarea;

public interface TareaRepository {
    public List<Tarea> getTareasByIdVoluntario(int idVolunteer);
    // CRUD
    public List<Tarea> getAllTareas();
    public Tarea getTareaById(int id);
    public int deleteById(int id);
    public int deleteAll();
    public Tarea post(Tarea newTarea);
    public Tarea put(int id, Tarea newtarea);
    public List<Tarea> getByIdEmergency(int id);
}
