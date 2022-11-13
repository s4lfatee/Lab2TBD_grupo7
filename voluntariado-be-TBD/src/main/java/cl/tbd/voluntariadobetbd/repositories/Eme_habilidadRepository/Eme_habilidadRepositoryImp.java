package cl.tbd.voluntariadobetbd.repositories.Eme_habilidadRepository;

import cl.tbd.voluntariadobetbd.models.Eme_habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Eme_habilidadRepositoryImp implements Eme_habilidadRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Eme_habilidad> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM eme_habilidad").executeAndFetch(Eme_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Eme_habilidad getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM eme_habilidad WHERE eme_habilidad.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Eme_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Eme_habilidad post(Eme_habilidad Eme_habilidad){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO eme_habilidad (id_emergencia, id_habilidad) values  (:Eme_habilidadId_emergencia,:Eme_habilidadId_habilidad)",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("Eme_habilidadId_emergencia", Eme_habilidad.getId_emergencia())
                    .addParameter("Eme_habilidadId_habilidad", Eme_habilidad.getId_habilidad())
                    .executeUpdate()
                    .getKey();
            Eme_habilidad.setId(insertId);
            return Eme_habilidad;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Eme_habilidad put(int id, Eme_habilidad Eme_habilidad){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE Eme_habilidad SET id_emergencia = :id_emergencia , id_habilidad = :id_habilidad WHERE Eme_habilidad.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    //agregar parametro con los nombres que se puso anteriormente con el gent adecuado
                    .addParameter("id_emergencia",Eme_habilidad.getId_emergencia())
                    .addParameter("id_habilidad",Eme_habilidad.getId_habilidad())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            Eme_habilidad.setId(insertId);
            return Eme_habilidad;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * if the Eme_habilidad is deleted then the return is 1, otherwise 0
     * */
    @Override
    public int deleteAll(){
        try(Connection conn = sql2o.open()){
            //tiene que ser igual para todos pero con el nombre de la tabla
            conn.createQuery("DELETE FROM eme_habilidad")
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /*
     * if the Eme_habilidad is deleted then the return is 1,
     * if the Eme_habilidad isn't exists then the return is 0,
     * otherwise -1
     * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            //nombre de la tabla con el nombre del id como esta puesto en la tabla
            int result = conn.createQuery("DELETE FROM eme_habilidad WHERE id = :id")
                    .addParameter("id",id)
                    .executeUpdate()
                    .getResult();
            return result;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
