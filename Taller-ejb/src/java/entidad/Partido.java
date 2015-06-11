package entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import partido.util.EstadoPartido;

@Entity
public class Partido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @NotNull
    private Equipo equipoA;
    @NotBlank
    @NotNull
    private Equipo equipoB;
    @NotBlank
    @NotNull
    private Integer golesA;
    @NotBlank
    @NotNull
    private Integer golesB;
    @NotBlank
    @NotNull
    private Date fechaF;
    @NotBlank
    @NotNull
    private Date fechaI;
    @NotBlank
    @NotNull
    private Integer minutos;
    @NotBlank
    @NotNull
    private EstadoPartido estado;
      
    
    
    public Partido() {
    }

    public Partido(Long id, Equipo equipoA, Equipo equipoB, Date fechaF, Date fechaI, EstadoPartido estado) {
        this.id = id;
        this.equipoA = equipoA;
        this.equipoB = equipoB;
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

    public Equipo getEquipoA() {
        return equipoA;
    }

    public void setEquipoA(Equipo equipoA) {
        this.equipoA = equipoA;
    }

    public Equipo getEquipoB() {
        return equipoB;
    }

    public void setEquipoB(Equipo equipoB) {
        this.equipoB = equipoB;
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

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }
    
    public EstadoPartido getEstado(){
        return estado;
    }
    
    public void setEsadoParido(EstadoPartido estado){
        this.estado = estado;
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.equipoA != other.equipoA && (this.equipoA == null || !this.equipoA.equals(other.equipoA))) {
            return false;
        }
        if (this.equipoB != other.equipoB && (this.equipoB == null || !this.equipoB.equals(other.equipoB))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Partido{" + "equipoA=" + equipoA + ", equipoB=" + equipoB + ", golesA=" + golesA + ", golesB=" + golesB + '}';
    }
    
    
    
  
    
}
