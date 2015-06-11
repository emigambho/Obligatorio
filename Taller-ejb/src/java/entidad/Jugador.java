
package entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Jugador extends Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
          
    @ManyToMany(cascade = CascadeType.ALL) 
    @JoinTable(name="jugador_equipo", 
            joinColumns = @JoinColumn(name="jugador_id", 
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="equipo_id", 
                    referencedColumnName = "id"))
    private List<Equipo> equipos;
    
    @NotNull
    private Integer puntuacion;
    
    public Jugador(){
    
    }

    public Jugador(Long id, String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono,Integer puntuacion,
            String email,String contrasenia,String direccion) {
        super(telefono,nombre,email,contrasenia,direccion,fechaNacimiento);
        this.id = id;
        this.equipos = equipos;
        this.puntuacion = puntuacion;
    }
    
    public Long getId() {
        return id;
    }
    

    public void setId(Long id) {
        this.id = id;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }
        
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Jugador)) {
            return false;
        }
        Jugador other = (Jugador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Jugador[ id=" + id + " ]";
    }
    
}
