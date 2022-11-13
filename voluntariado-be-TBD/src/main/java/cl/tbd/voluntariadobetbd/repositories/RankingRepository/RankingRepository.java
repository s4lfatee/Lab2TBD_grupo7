package cl.tbd.voluntariadobetbd.repositories.RankingRepository;

import java.util.List;
import cl.tbd.voluntariadobetbd.models.Ranking;

public interface RankingRepository {
    public List<Ranking> getAll();
    public Ranking getById(int id);
    public Ranking post(Ranking Ranking);
    public Ranking put(int id, Ranking Ranking);
    public int deleteAll();
    public int deleteById(int id);
    public int updateStateTarea(int idvoluntario, int idTarea, int newState);
}
