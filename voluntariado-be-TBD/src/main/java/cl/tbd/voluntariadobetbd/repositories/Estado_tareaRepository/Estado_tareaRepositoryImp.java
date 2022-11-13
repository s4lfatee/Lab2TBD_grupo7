package cl.tbd.voluntariadobetbd.repositories.Estado_tareaRepository;

import cl.tbd.voluntariadobetbd.models.Estado_tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Estado_tareaRepositoryImp implements Estado_tareaRepository {
    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Estado_tarea> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM estado_tarea").executeAndFetch(Estado_tarea.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Estado_tarea getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM estado_tarea WHERE estado_tarea.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Estado_tarea.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Estado_tarea post(Estado_tarea estado_tarea){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO estado_tarea (descrip) values  (:descrip)",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("descrip", estado_tarea.getDescrip())
                    .executeUpdate()
                    .getKey();
            estado_tarea.setId(insertId);
            return estado_tarea;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Estado_tarea put(int id, Estado_tarea estado_tarea){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE estado_tarea SET descrip = :descrip WHERE estado_tarea.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    .addParameter("descrip",estado_tarea.getDescrip())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            estado_tarea.setId(insertId);
            return estado_tarea;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    /*
     * if the Emergencia is deleted then the return is 1, otherwise 0
     * */
    @Override
    public int deleteAll(){
        try(Connection conn = sql2o.open()){
            //tiene que ser igual para todos pero con el nombre de la tabla
            conn.createQuery("DELETE FROM estado_tarea")
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
    /*
     * if the Emergencia is deleted then the return is 1,
     * if the Emergencia isn't exists then the return is 0,
     * otherwise -1
     * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            //nombre de la tabla con el nombre del id como esta puesto en la tabla
            conn.createQuery("DELETE FROM estado_tarea WHERE id = :id")
                    .addParameter("id",id)
                    .executeUpdate()
                    .getResult();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
