package entidad;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

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
    private Date Fecha;
    @NotBlank
    @NotNull
    private Integer HoraI;
    @NotBlank
    @NotNull
    private Integer Minutos;
    @NotBlank
    @NotNull
    private Integer HoraF;
   
    
    
    public Partido() {
    }

    public Partido(Long id, Equipo equipoA, Equipo equipoB,Date Fecha, Integer HoraI, Integer HoraF) {
        this.id = id;
        this.equipoA = equipoA;
        this.equipoB = equipoB;
        this.golesA = 0;
        this.golesB = 0;
        this.Fecha = Fecha;
        this.HoraI = HoraI;
        this.Minutos = 0;
        this.HoraF = HoraF;
    }



    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Integer getHoraI() {
        return HoraI;
    }

    public void setHoraI(Integer HoraI) {
        this.HoraI = HoraI;
    }

    public Integer getMinutos() {
        return Minutos;
    }

    public void setMinutos(Integer Minutos) {
        this.Minutos = Minutos;
    }

    public Integer getHoraF() {
        return HoraF;
    }

    public void setHoraF(Integer HoraF) {
        this.HoraF = HoraF;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partido)) {
            return false;
        }
        Partido other = (Partido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidad.Partido[ id=" + id + " ]";
    }
    
}
