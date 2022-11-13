package cl.tbd.voluntariadobetbd.repositories.Tarea_habilidadRepository;

import cl.tbd.voluntariadobetbd.models.Tarea_habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Tarea_habilidadRepositoryImp implements Tarea_habilidadRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Tarea_habilidad> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM tarea_habilidad").executeAndFetch(Tarea_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Tarea_habilidad getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM tarea_habilidad WHERE tarea_habilidad.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Tarea_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Tarea_habilidad post(Tarea_habilidad Tarea_habilidad){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO tarea_habilidad (id_emehab, id_tarea) values  (:Tarea_habilidadId_emehab,:Tarea_habilidadId_tarea)",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("Tarea_habilidadId_emehab", Tarea_habilidad.getId_emehab())
                    .addParameter("Tarea_habilidadId_tarea", Tarea_habilidad.getId_tarea())
                    .executeUpdate()
                    .getKey();
            Tarea_habilidad.setId(insertId);
            return Tarea_habilidad;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Tarea_habilidad put(int id, Tarea_habilidad Tarea_habilidad){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE Tarea_habilidad SET id_emehab = :id_emehab , id_tarea = :id_tarea WHERE Tarea_habilidad.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    //agregar parametro con los nombres que se puso anteriormente con el gent adecuado
                    .addParameter("id_emehab",Tarea_habilidad.getId_emehab())
                    .addParameter("id_tarea",Tarea_habilidad.getId_tarea())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            Tarea_habilidad.setId(insertId);
            return Tarea_habilidad;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * if the Tarea_habilidad is deleted then the return is 1, otherwise 0
     * */
    @Override
    public int deleteAll(){
        try(Connection conn = sql2o.open()){
            //tiene que ser igual para todos pero con el nombre de la tabla
            conn.createQuery("DELETE FROM tarea_habilidad")
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /*
     * if the Tarea_habilidad is deleted then the return is 1,
     * if the Tarea_habilidad isn't exists then the return is 0,
     * otherwise -1
     * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            //nombre de la tabla con el nombre del id como esta puesto en la tabla
            int result = conn.createQuery("DELETE FROM tarea_habilidad WHERE id = :id")
                    .addParameter("id",id)
                    .executeUpdate()
                    .getResult();
            return result;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    };
}
