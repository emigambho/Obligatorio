package entidad;

import org.hibernate.validator.constraints.NotBlank;

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
import javax.validation.constraints.NotNull;

@Entity
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer clasificacion;

    @NotNull
    @NotBlank
    private String color;

    @NotNull
    @NotBlank
    private String nombre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "equipo_partido",
            joinColumns = @JoinColumn(name = "equipo_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "partido_id",
                    referencedColumnName = "id"))
    private List<Partido> partidos;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "jugador_equipo",
            joinColumns = @JoinColumn(name = "equipo_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "jugador_id",
                    referencedColumnName = "id"))
    private List<Jugador> jugadores;

    public Equipo() {
    }

    public Equipo(List<Jugador> jugadores, Integer clasificacion, String color, List<Partido> partidos, String nombre) {
        this.color = color;
        this.partidos = partidos;
        this.jugadores = jugadores;
        this.clasificacion = clasificacion;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public Integer getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(Integer clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id != null ? id.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Equipo)) {
            return false;
        }
        Equipo other = (Equipo) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "entidad.Equipo[ id=" + id + " ]";
    }

}
