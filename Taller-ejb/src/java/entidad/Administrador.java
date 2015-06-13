
package entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Administrador extends Usuario implements Serializable{
   
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Local> locales;
    
    public Administrador(){}

    public Administrador(Long id, List<Local> locales, Integer telefono, String nombre, String email, String contrasenia, String direccion, Date fechaNacimiento) {
        super(id,telefono, nombre, email, contrasenia, direccion, fechaNacimiento);
        this.locales = locales;
    }

    public List<Local> getLocales() {
        return locales;
    }

    public void setLocales(List<Local> locales) {
        this.locales = locales;
    }
}
