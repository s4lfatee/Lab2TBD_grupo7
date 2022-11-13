package cl.tbd.voluntariadobetbd.repositories.EmergenciaRepository;

import java.util.List;
import java.sql.Date;
import cl.tbd.voluntariadobetbd.models.Emergencia;

import javax.swing.*;

public interface EmergenciaRepository {
    public List<Emergencia> getAll();
    public Emergencia getById(int id);
    public Emergencia post(Emergencia Emergencia);
    public Emergencia put(int id, Emergencia Emergencia);
    public int deleteAll();
    public int deleteById(int id);
    public List<Emergencia> getNearBy(int id);

    public List<Emergencia> getBetweenDates(Date inicio, Date fin);
}
