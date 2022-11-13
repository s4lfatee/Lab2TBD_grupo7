package cl.tbd.voluntariadobetbd.repositories.Vol_habilidadRepository;

import cl.tbd.voluntariadobetbd.models.Vol_habilidad;

import java.util.List;

public interface Vol_habilidadRepository {
    public List<Vol_habilidad> getAll();
    public Vol_habilidad getById(int id);
    public Vol_habilidad post(Vol_habilidad Vol_habilidad);
    public Vol_habilidad put(int id, Vol_habilidad Vol_habilidad);
    public int deleteAll();
    public int deleteById(int id);
}
