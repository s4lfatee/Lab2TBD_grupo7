package cl.tbd.voluntariadobetbd.repositories.InstitucionRepository;

import java.util.List;
import cl.tbd.voluntariadobetbd.models.Institucion;

public interface InstitucionRepository {
    public List<Institucion> getAll();
    public Institucion getById(int id);
    public Institucion post(Institucion institucion);
    public Institucion put(int id, Institucion institucion);
    public int deleteAll();
    public int deleteById(int id);
}
