package entidad;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solicitud {
    
    Long id;
    Auto auto;
    Empleado receptor;
    Date fechaRecepcion;
    List<Servicio> servicios;

    public Solicitud() {
    }

    public Solicitud(Long id, Auto auto, Empleado receptor, Date fechaRecepcion) {
        this.id = id;
        this.auto = auto;
        this.receptor = receptor;
        this.fechaRecepcion = fechaRecepcion;
        this.servicios = new ArrayList<Servicio>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Empleado getReceptor() {
        return receptor;
    }

    public void setReceptor(Empleado receptor) {
        this.receptor = receptor;
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public List<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
    
    public void addToServicios(Servicio servicio) {
        this.servicios.add(servicio);
    }
    
}