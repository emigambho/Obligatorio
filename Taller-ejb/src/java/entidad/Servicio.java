package entidad;

public class Servicio {
    
    String descripcion;
    String estado;

    public Servicio() {
    }
    
    public Servicio(String descripcion, String estado) {
        this.descripcion = descripcion;
        this.estado = "PENDIENTE";
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}