package SessionBean;

import entidad.Administrador;
import entidad.Equipo;
import entidad.Jugador;
import java.util.Date;
import java.util.List;
import entidad.Usuario;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class UsuarioBean {

    @PersistenceContext
    EntityManager em;
    
    private Map<String,Usuario> usuarios;

    public UsuarioBean() {
        this.usuarios = new HashMap<String, Usuario>();
    }
    
    private void guardarUsuario(Usuario usuario){
        byte[] bytesEncoded = Base64.getEncoder().encode((usuario.getEmail()+usuario.getContrasenia()).getBytes());
        String token = new String(bytesEncoded);
        usuarios.put(token,usuario);
    }

    public Administrador CrearAdministrador(String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono,Integer puntuacion,
            String email,String contrasenia,String direccion) {
        Administrador administrador = new Administrador(null, telefono, nombre, email, contrasenia, direccion, fechaNacimiento);
        em.persist(administrador);
        return administrador;
    }

    public Jugador CrearJugador(String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono,Integer puntuacion,
            String email,String contrasenia,String direccion) {
        Jugador jugador = new Jugador(nombre,fechaNacimiento,equipos,telefono,puntuacion,email,contrasenia,direccion);
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
        return (Administrador)em.createQuery("select a from Administrador a where a.email = :email and a.contrasenia = :contrasenia")
        .setParameter("email", email).setParameter("contrasenia", contrasenia).getSingleResult();
    }
    
    public Jugador IniciarSesionJugador(String email, String contrasenia) {
          return (Jugador)em.createQuery("select j from Jugador j where j.email = :email and j.contrasenia = :contrasenia")
                .setParameter("email", email).setParameter("contrasenia", contrasenia).getSingleResult();
    }
    
   
    public Usuario buscarUsuario(String token) {
        return usuarios.get(token);
    }

}
