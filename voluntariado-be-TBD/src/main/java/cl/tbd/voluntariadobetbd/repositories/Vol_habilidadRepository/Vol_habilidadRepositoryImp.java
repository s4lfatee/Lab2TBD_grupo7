package cl.tbd.voluntariadobetbd.repositories.Vol_habilidadRepository;

import cl.tbd.voluntariadobetbd.models.Vol_habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class Vol_habilidadRepositoryImp implements Vol_habilidadRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Vol_habilidad> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM vol_habilidad").executeAndFetch(Vol_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Vol_habilidad getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM vol_habilidad WHERE vol_habilidad.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Vol_habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Vol_habilidad post(Vol_habilidad vol_habilidad){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO vol_habilidad (id_voluntario, id_habilidad) values  (:id_voluntario, :id_habilidad)",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("id_voluntario", vol_habilidad.getId_voluntario())
                    .addParameter("id_habilidad", vol_habilidad.getId_voluntario())
                    .executeUpdate()
                    .getKey();
            vol_habilidad.setId(insertId);
            return vol_habilidad;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Vol_habilidad put(int id, Vol_habilidad vol_habilidad){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE vol_habilidad SET id_voluntario = :id_voluntario, id_habilidad = :id_habilidad WHERE vol_habilidad.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    //agregar parametro con los nombres que se puso anteriormente con el gent adecuado
                    .addParameter("id_voluntario",vol_habilidad.getId_voluntario())
                    .addParameter("id_habilidad",vol_habilidad.getId_habilidad())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            vol_habilidad.setId(insertId);
            return vol_habilidad;

        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * if the Habilidad is deleted then the return is 1, otherwise 0
     * */
    @Override
    public int deleteAll(){
        try(Connection conn = sql2o.open()){
            //tiene que ser igual para todos pero con el nombre de la tabla
            conn.createQuery("DELETE FROM habilidad")
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /*
     * if the vol_habilidad is deleted then the return is 1,
     * if the vol_habilidad isn't exists then the return is 0,
     * otherwise -1
     * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            //nombre de la tabla con el nombre del id como esta puesto en la tabla
            int result = conn.createQuery("DELETE FROM vol_habilidad WHERE id = :id")
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
