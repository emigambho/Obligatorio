package SessionBean;

import entidad.Administrador;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
import java.util.Date;
import java.util.List;
import entidad.Usuario;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UsuarioBean {

    @PersistenceContext
    EntityManager em;


    public Administrador CrearAdministrador(Long id,String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono,Integer puntuacion,
            String email,String contrasenia,String direccion) {
        //para el contructor presisa el ID
        Administrador administrador = new Administrador(id, null, telefono, nombre, email, contrasenia, direccion, fechaNacimiento);
        em.persist(administrador);
        return administrador;
    }

    public Jugador CrearJugador(Long id,String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono,Integer puntuacion,
            String email,String contrasenia,String direccion) {
        //para el contructor presisa el ID
        Jugador jugador = new Jugador(id,nombre,fechaNacimiento,equipos,telefono,puntuacion,email,contrasenia,direccion);
        em.persist(jugador);
        return jugador;
    }

    public List<Administrador> ListarAdministradores() {
        return em.createQuery("select a from Administrador a").getResultList();
    }

    public List<Jugador> ListarJugadores() {
        return em.createQuery("select j from Jugador j").getResultList();
    }
    
    public Administrador IniciarSesionAdministrador(String email, String contrasenia){
        return (Administrador)em.createQuery("select a from Administrador a where email = :email and contrasenia = :contrasenia")
        .setParameter("email", email).setParameter("contrasenia", contrasenia).getSingleResult();
    }
    
    public Jugador IniciarSesionJugador(String email, String contrasenia) {
          return (Jugador)em.createQuery("select j from Jugador j where email = :email and contrasenia = :contrasenia")
                .setParameter("email", email).setParameter("contrasenia", contrasenia).getSingleResult();
    }
    
   
    public Usuario buscarUsuario(String token) {
        return null;
    }

}
