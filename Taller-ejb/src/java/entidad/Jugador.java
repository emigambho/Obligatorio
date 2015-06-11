
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
public class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @NotBlank
    private String nombre;
  
    @Past
    @Temporal(TemporalType.DATE)
    private Date FechaNacimiento;
    
    @NotNull
    @NotBlank
    private Integer telefono;
    
    @ManyToMany(cascade = CascadeType.ALL) 
    @JoinTable(name="jugador_equipo", 
            joinColumns = @JoinColumn(name="jugador_id", 
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="equipo_id", 
                    referencedColumnName = "id"))
    private List<Equipo> equipos;

    public Jugador() {
    }

    public Jugador(Long id, String nombre, Date FechaNacimiento, List<Equipo> equipos, Integer telefono) {
        this.id = id;
        this.telefono = telefono;
        this.nombre = nombre;
        this.FechaNacimiento = FechaNacimiento;
        this.equipos = equipos;
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

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
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
