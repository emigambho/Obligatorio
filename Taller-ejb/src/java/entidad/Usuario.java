package entidad;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.NotBlank;

public class Usuario{
    
    @NotNull
    private Integer telefono;
    
    @NotNull
    @NotBlank
    private String nombre;
    
    @NotNull
    @NotBlank
    private String email;
     
    @NotNull
    @NotBlank
    private String contrasenia;
    
    @NotNull
    @NotBlank
    private String direccion;
    
    @Past
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    
    public Usuario(){}

    public Usuario(Integer telefono, String nombre, String email, String contrasenia, String direccion,  Date fechaNacimiento) {
        this.telefono = telefono;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }
        
    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    
    
    
}
