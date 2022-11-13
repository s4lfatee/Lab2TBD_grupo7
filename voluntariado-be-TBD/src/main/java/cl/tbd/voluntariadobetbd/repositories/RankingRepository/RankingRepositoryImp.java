package cl.tbd.voluntariadobetbd.repositories.RankingRepository;

import cl.tbd.voluntariadobetbd.models.Estado_tarea;
import cl.tbd.voluntariadobetbd.models.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class RankingRepositoryImp implements RankingRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Ranking> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM ranking").executeAndFetch(Ranking.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Ranking getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM ranking WHERE ranking.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Ranking.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Ranking post(Ranking Ranking){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO ranking (id_voluntario, id_tarea, puntaje, flg_invitado, flg_participa) values  (:RankingId_voluntario,:RankingId_tarea, :RankingPuntaje, :RankingFlg_invitado, :RankingFlg_participa)",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("RankingId_voluntario", Ranking.getId_voluntario())
                    .addParameter("RankingId_tarea", Ranking.getId_tarea())
                    .addParameter("RankingPuntaje", Ranking.getPuntaje())
                    .addParameter("RankingFlg_invitado", Ranking.getFlg_invitado())
                    .addParameter("RankingFlg_participa", Ranking.getFlg_participa())
                    .executeUpdate()
                    .getKey();
            Ranking.setId(insertId);
            return Ranking;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Ranking put(int id, Ranking Ranking){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE Ranking SET id_voluntario = :id_voluntario , id_tarea = :id_tarea , puntaje = :puntaje , flg_invitado = :flg_invitado , flg_participa = :flg_participa WHERE Ranking.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    //agregar parametro con los nombres que se puso anteriormente con el gent adecuado
                    .addParameter("id_voluntario",Ranking.getId_voluntario())
                    .addParameter("id_tarea",Ranking.getId_tarea())
                    .addParameter("puntaje",Ranking.getPuntaje())
                    .addParameter("flg_invitado",Ranking.getFlg_invitado())
                    .addParameter("flg_participa",Ranking.getFlg_participa())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            Ranking.setId(insertId);
            return Ranking;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public int updateStateTarea(int idvoluntario, int idTarea, int newState){
        String queryUpdate = "UPDATE ranking SET flg_participa = :id_participa WHERE" +
                "id_voluntario = :id_voluntario AND id_tarea = :id_tarea";
        try(Connection conn = sql2o.open()){
            conn.createQuery(queryUpdate,true)
                    .addParameter("id_tarea", idTarea)
                    .addParameter("id_participa",newState)
                    .addParameter("id_voluntario",idvoluntario)
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.print(e.getMessage());
            return 0;
        }
    }

    /*
     * if the Ranking is deleted then the return is 1, otherwise 0
     * */
    @Override
    public int deleteAll(){
        try(Connection conn = sql2o.open()){
            //tiene que ser igual para todos pero con el nombre de la tabla
            conn.createQuery("DELETE FROM ranking")
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /*
     * if the Ranking is deleted then the return is 1,
     * if the Ranking isn't exists then the return is 0,
     * otherwise -1
     * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            //nombre de la tabla con el nombre del id como esta puesto en la tabla
            conn.createQuery("DELETE FROM ranking WHERE id = :id")
                    .addParameter("id",id)
                    .executeUpdate()
                    .getResult();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    };
}
