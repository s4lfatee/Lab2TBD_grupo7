package cl.tbd.voluntariadobetbd.models;

import java.sql.Date;

public class Tarea {
    private int id;
    private String nombre;
    private String descrip;
    private int cant_vol_requeridos;
    private int cant_vol_inscritos;
    private int id_emergencia;
    private Date finicio;
    private Date ffin;

    private int id_estado;
    public int getId(){
        return this.id;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setId(int newId){
        this.id = newId;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescrip() {
        return descrip;
    }
    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }
    public int getCant_vol_requeridos() {
        return cant_vol_requeridos;
    }
    public void setCant_vol_requeridos(int cant_vol_requeridos) {
        this.cant_vol_requeridos = cant_vol_requeridos;
    }
    public int getCant_vol_inscritos() {
        return cant_vol_inscritos;
    }
    public void setCant_vol_inscritos(int cant_vol_inscritos) {
        this.cant_vol_inscritos = cant_vol_inscritos;
    }
    public int getId_emergencia() {
        return id_emergencia;
    }
    public void setId_emergencia(int id_emergencia) {
        this.id_emergencia = id_emergencia;
    }
    public Date getFinicio() {
        return finicio;
    }
    public void setFinicio(Date finicio) {
        this.finicio = finicio;
    }
    public Date getFfin() {
        return ffin;
    }
    public void setFfin(Date ffin) {
        this.ffin = ffin;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }
}
