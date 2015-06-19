
package entidad;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;


@Entity
public class Local implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private Integer horaApertura;
    
    @NotNull
    private Integer horaCierre;
    
    @NotBlank
    @NotNull
    private String direccion;
    
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "local")
    private List<Cancha> canchas;
    
    @NotBlank
    @NotNull
    private Integer telefono;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="local_administrador", 
            joinColumns = @JoinColumn(name="local_id", 
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="administrador_id", 
                    referencedColumnName = "id"))
    private List<Administrador> administradores;

    public Local()
    {
    
    }
    
    public Local(String direccion, List<Cancha> canchas, Integer telefono, Integer horaApertura, Integer horaCierre) {
        this.direccion = direccion;
        this.canchas = canchas;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }
 
     public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCanchas(List<Cancha> canchas) {
        this.canchas = canchas;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    } 
    public String getDireccion() {
        return direccion;
    }

    public List<Cancha> getCanchas() {
        return canchas;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public Integer getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(Integer horaApertura) {
        this.horaApertura = horaApertura;
    }

    public Integer getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Integer horaCierre) {
        this.horaCierre = horaCierre;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Local other = (Local) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Local{" + "id=" + id + ", direccion=" + direccion + ", telefono=" + telefono + '}';
    }
    
    
    
}
