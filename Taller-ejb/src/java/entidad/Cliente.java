package entidad;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;

public class Cliente {
    
    Long id;
    String nombre;
    
    @OneToMany
    List<Auto> autos;

    public Cliente() {
        this.autos = new ArrayList<Auto>();
    }
    
    public Cliente(Long id, String n) {
        this.id = id;
        this.nombre = n;
        this.autos = new ArrayList<Auto>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public void setAutos(List<Auto> autos) {
        this.autos = autos;
    }
    
    public void addToAutos(Auto auto) {
        this.autos.add(auto);
    }
    
}