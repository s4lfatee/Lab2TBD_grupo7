package cl.tbd.voluntariadobetbd.repositories.HabilidadRepository;

import cl.tbd.voluntariadobetbd.models.Habilidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.Date;
import java.util.List;

@Repository
public class HabilidadRepositoryImp implements HabilidadRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Habilidad> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM habilidad").executeAndFetch(Habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Habilidad getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM habilidad WHERE habilidad.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Habilidad.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Habilidad post(Habilidad Habilidad){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO habilidad (descrip) values  (:HabilidadDescrip)",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("HabilidadDescrip", Habilidad.getDescrip())
                    .executeUpdate()
                    .getKey();
            Habilidad.setId(insertId);
            return Habilidad;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Habilidad put(int id, Habilidad Habilidad){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE Habilidad SET descrip = :descrip WHERE Habilidad.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    //agregar parametro con los nombres que se puso anteriormente con el gent adecuado
                    .addParameter("descrip",Habilidad.getDescrip())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            Habilidad.setId(insertId);
            return Habilidad;

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
     * if the Habilidad is deleted then the return is 1,
     * if the Habilidad isn't exists then the return is 0,
     * otherwise -1
     * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            //nombre de la tabla con el nombre del id como esta puesto en la tabla
            int result = conn.createQuery("DELETE FROM habilidad WHERE id = :id")
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
