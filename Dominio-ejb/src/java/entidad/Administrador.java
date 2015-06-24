package entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Administrador extends Usuario implements Serializable {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "local_administrador",
            joinColumns = @JoinColumn(name = "administrador_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "local_id",
                    referencedColumnName = "id"))
    private List<Local> locales;

    public Administrador() {
    }

    public Administrador(List<Local> locales, Integer telefono, String nombre,
            String email, String contrasenia, String direccion, Date fechaNacimiento) {
        super(telefono, nombre, email, contrasenia, direccion, fechaNacimiento);
        this.locales = locales;
    }

    public List<Local> getLocales() {
        return locales;
    }

    public void setLocales(List<Local> locales) {
        this.locales = locales;
    }
}
