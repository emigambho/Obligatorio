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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Cancha implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    private String tipo;

    @NotNull
    private Double tamanio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cancha")
    private List<Partido> partidos;

    @ManyToOne
    @JoinColumn(name = "LOCAL_ID")
    private Local local;

    @NotNull
    private Boolean habilitado;

    public Cancha() {
    }

    public Cancha(String tipo, Double tamanio, List<Partido> partidos, Boolean habilitado) {
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.partidos = partidos;
        this.habilitado = habilitado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTamanio(Double tamanio) {
        this.tamanio = tamanio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public Double getTamanio() {
        return tamanio;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public Boolean getHabilitado() {
        return habilitado;
    }

    public void setHabilitado(Boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += id != null ? id.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cancha)) {
            return false;
        }
        Cancha other = (Cancha) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "entidad.Cancha[ id=" + id + " ]";
    }

}
