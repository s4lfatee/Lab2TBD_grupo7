package cl.tbd.voluntariadobetbd.repositories.EmergenciaRepository;

import cl.tbd.voluntariadobetbd.models.Emergencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import java.sql.Date;
import java.util.List;

@Repository
public class EmergenciaRepositoryImp implements EmergenciaRepository{

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Emergencia> getAll(){
        final String query = "SELECT e.id, e.nombre, e.descrip, e.finicio, e.Date, e.id_institucion, ST_Y(e.location) AS latitud, ST_X(e.location) AS longitud FROM emergencia AS e";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query).executeAndFetch(Emergencia.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Emergencia> getNearBy(int id){
        final String query = "SELECT e.id, e.nombre, e.descrip, e.finicio, e.Date, e.id_institucion, ST_Y(e.location) AS latitud, ST_X(e.location) AS longitud FROM voluntario AS v, division_regional AS r, emergencia as e WHERE ST_WITHIN(v.\"location\", r.geom) AND v.id= :id AND ST_WITHIN(e.\"location\", r.geom)";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query)
                    .addParameter("id",id)
                    .executeAndFetch(Emergencia.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Emergencia> getBetweenDates(Date inicio, Date fin){
        final String query = "SELECT e.id, e.nombre, e.descrip, e.finicio, e.Date, e.id_institucion, ST_Y(e.location) AS latitud, ST_X(e.location) AS longitud FROM emergencia AS e WHERE :finicio BETWEEN e.finicio AND e.inicio AND :ffinal BETWEEN e.finicio AND e.inicio";
        try(Connection conn = sql2o.open()){
            return conn.createQuery(query)
                    .addParameter("finicio",inicio)
                    .addParameter("ffinal",fin)
                    .executeAndFetch(Emergencia.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Emergencia getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT e.id, e.nombre, e.descrip, e.finicio, e.Date, e.id_institucion, ST_Y(e.location) AS latitud, ST_X(e.location) AS longitud FROM emergencia AS e WHERE e.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Emergencia.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Emergencia post(Emergencia Emergencia){
        try(Connection conn = sql2o.open()){
            //cada vex que haces uno de repositorioimp cambia los parametros que estan en la tabla y luego los pones como esta aqui
            int insertId = (int) conn.createQuery(
                            "INSERT INTO emergencia (nombre, descrip, finicio, ffin, id_institucion, location) values  (:EmergenciaNombre,:EmergenciaDescrip, :EmergenciaFinicio, :EmergenciaFfin, :EmergenciaId_institucion, ST_SetSRID(ST_MakePoint(:EmergenciaLongitud,:EmergenciaLatitud), 4326))",true)
                    //Agregar cada de los parametros que pusiste anteriormente
                    .addParameter("EmergenciaNombre", Emergencia.getNombre())
                    .addParameter("EmergenciaDescrip", Emergencia.getDescrip())
                    .addParameter("EmergenciaFinicio", Emergencia.getFinicio())
                    .addParameter("EmergenciaFfin", Emergencia.getFfin())
                    .addParameter("EmergenciaId_institucion", Emergencia.getId_institucion())
                    .addParameter("EmergenciaLongitud",Emergencia.getLongitud())
                    .addParameter("EmergenciaLatitud",Emergencia.getLatitud())
                    .executeUpdate()
                    .getKey();
            Emergencia.setId(insertId);
            return Emergencia;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Emergencia put(int id, Emergencia Emergencia){
        // despues del set agregar por cada parametro algo como esto "nombre = :nombre"
        final String query = "UPDATE Emergencia SET nombre = :nombre , descrip = :descrip , finicio = :finicio , ffin = :ffin , id_institucion = :id_institucion, location = ST_SetSRID(ST_MakePoint(:EmergenciaLongitud,:EmergenciaLatitud), 4326) WHERE Emergencia.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    //agregar parametro con los nombres que se puso anteriormente con el gent adecuado
                    .addParameter("nombre",Emergencia.getNombre())
                    .addParameter("descrip",Emergencia.getDescrip())
                    .addParameter("finicio",Emergencia.getFinicio())
                    .addParameter("ffin",Emergencia.getFfin())
                    .addParameter("id_institucion",Emergencia.getId_institucion())
                    .addParameter("id",id)
                    .addParameter("EmergenciaLongitud",Emergencia.getLongitud())
                    .addParameter("EmergenciaLatitud",Emergencia.getLatitud())
                    .executeUpdate()
                    .getKey();
            Emergencia.setId(insertId);
            return Emergencia;

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
            conn.createQuery("DELETE FROM emergencia")
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
            conn.createQuery("DELETE FROM emergencia WHERE id = :id")
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
