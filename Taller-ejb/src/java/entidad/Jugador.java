
package entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
        super(id,telefono,nombre,email,contrasenia,direccion,fechaNacimiento);
        this.equipos = equipos;
        this.puntuacion = puntuacion;
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
     
    
}
