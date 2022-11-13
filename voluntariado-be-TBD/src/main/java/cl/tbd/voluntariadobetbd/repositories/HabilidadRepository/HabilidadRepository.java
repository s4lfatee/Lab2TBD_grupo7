package cl.tbd.voluntariadobetbd.repositories.HabilidadRepository;

import java.util.List;
import java.sql.Date;
import cl.tbd.voluntariadobetbd.models.Habilidad;

public interface HabilidadRepository {
    public List<Habilidad> getAll();
    public Habilidad getById(int id);
    public Habilidad post(Habilidad Habilidad);
    public Habilidad put(int id, Habilidad Habilidad);
    public int deleteAll();
    public int deleteById(int id);
}
