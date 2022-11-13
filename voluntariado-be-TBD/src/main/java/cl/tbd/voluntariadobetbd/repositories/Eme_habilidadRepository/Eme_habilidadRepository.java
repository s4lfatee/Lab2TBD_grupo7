package cl.tbd.voluntariadobetbd.repositories.Eme_habilidadRepository;

import java.util.List;
import cl.tbd.voluntariadobetbd.models.Eme_habilidad;

public interface Eme_habilidadRepository {
    public List<Eme_habilidad> getAll();
    public Eme_habilidad getById(int id);
    public Eme_habilidad post(Eme_habilidad Eme_habilidad);
    public Eme_habilidad put(int id, Eme_habilidad Eme_habilidad);
    public int deleteAll();
    public int deleteById(int id);
}