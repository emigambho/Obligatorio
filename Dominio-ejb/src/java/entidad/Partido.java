package entidad;

import partido.util.EstadoPartido;

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
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Partido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "equipo_partido",
            joinColumns = @JoinColumn(name = "partido_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipo_id",
                    referencedColumnName = "id"))
    private List<Equipo> equipos;

    @NotNull
    private Integer golesA;

    @NotNull
    private Integer golesB;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaF;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaI;

    @NotNull
    private EstadoPartido estado;

    @ManyToOne
    @JoinColumn(name = "CANCHA_ID")
    private Cancha cancha;

    public Partido() {
    }

    public Partido(List<Equipo> equipos, Date fechaF, Date fechaI, EstadoPartido estado) {
        this.equipos = equipos;
        this.golesA = 0;
        this.golesB = 0;
        this.fechaF = fechaF;
        this.fechaI = fechaI;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public Integer getGolesA() {
        return golesA;
    }

    public void setGolesA(Integer golesA) {
        this.golesA = golesA;
    }

    public Integer getGolesB() {
        return golesB;
    }

    public void setGolesB(Integer golesB) {
        this.golesB = golesB;
    }

    public Date getFechaF() {
        return fechaF;
    }

    public void setFechaF(Date fechaF) {
        this.fechaF = fechaF;
    }

    public Date getFechaI() {
        return fechaI;
    }

    public void setFechaI(Date fechaI) {
        this.fechaI = fechaI;
    }

    public EstadoPartido getEstado() {
        return estado;
    }

    public void setEsadoParido(EstadoPartido estado) {
        this.estado = estado;
    }

    public Cancha getCancha() {
        return cancha;
    }

    public void setCancha(Cancha cancha) {
        this.cancha = cancha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final Partido other = (Partido) obj;
        return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Partido{" + "equipoA=" + equipos.get(0) + ", equipoB=" + equipos.get(0) 
                + ", golesA=" + golesA + ", golesB=" + golesB + '}';
    }

}
