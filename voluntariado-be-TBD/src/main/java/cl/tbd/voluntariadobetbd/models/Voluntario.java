package cl.tbd.voluntariadobetbd.models;

import java.sql.Date;

public class Voluntario {
    private Integer id;
    private String nombre;
    private Date fnacimiento;
    private double latitud;
    private double longitud;
    public Integer getId(){
        return this.id;
    }
    public String getNombre(){
        return this.nombre;
    }
    public void setId(Integer newId){
        this.id = newId;
    }
    public void setNombre(String newName){
        this.nombre = newName;
    }
    public Date getFnacimiento() {
        return fnacimiento;
    }

    public void setFnacimiento(Date fnacimiento) {
        this.fnacimiento = fnacimiento;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
