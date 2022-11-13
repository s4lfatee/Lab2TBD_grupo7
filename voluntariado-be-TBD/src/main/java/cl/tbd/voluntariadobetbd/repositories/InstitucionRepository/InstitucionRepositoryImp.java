package cl.tbd.voluntariadobetbd.repositories.InstitucionRepository;

import cl.tbd.voluntariadobetbd.models.Institucion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

@Repository
public class InstitucionRepositoryImp implements InstitucionRepository {

    @Autowired
    private Sql2o sql2o;

    @Override
    public List<Institucion> getAll(){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM institucion")
                    .executeAndFetch(Institucion.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Institucion getById(int id){
        try(Connection conn = sql2o.open()){
            return conn.createQuery("SELECT * FROM institucion WHERE institucion.id = :id")
                    .addParameter("id",id)
                    .executeAndFetchFirst(Institucion.class);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @Override
    public Institucion post(Institucion institucion){
        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(
                    "INSERT INTO institucion (nombre, descrip) values  (:institutionName, :institutionDescrip)",true)
                    .addParameter("institutionName", institucion.getNombre())
                    .addParameter("institutionDescrip", institucion.getDescrip())
                    .executeUpdate()
                    .getKey();
            institucion.setId(insertId);
            return institucion;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    @Override
    public Institucion put(int id, Institucion institucion){

        final String query = "UPDATE institucion SET nombre = :nombre, descrip = :descrip  WHERE institucion.id = :id";

        try(Connection conn = sql2o.open()){
            int insertId = (int) conn.createQuery(query)
                    .addParameter("nombre", institucion.getNombre())
                    .addParameter("descrip", institucion.getDescrip())
                    .addParameter("id",id)
                    .executeUpdate()
                    .getKey();
            institucion.setId(insertId);
            return institucion;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * if the institution is deleted then the return is 1, otherwise 0
     * */
    @Override
    public int deleteAll(){
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM institucion")
                    .executeUpdate();
            return 1;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /*
    * if the institution is deleted then the return is 1,
    * if the institution isn't exists then the return is 0,
    * otherwise -1
    * */
    @Override
    public int deleteById(int id){
        try(Connection conn = sql2o.open()){
            conn.createQuery("DELETE FROM institution WHERE id_institution = :id")
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
