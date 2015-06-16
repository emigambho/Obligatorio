package SessionBean;

import entidad.Administrador;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
import java.util.Date;
import java.util.List;
import entidad.Usuario;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import usuario.util.UsuarioOAuth;

@Singleton
public class UsuarioBean {

    @PersistenceContext
    EntityManager em;

    private Map<String, UsuarioOAuth> usuarios;

    public UsuarioBean() {
        this.usuarios = new HashMap<String, UsuarioOAuth>();
    }

    private UsuarioOAuth guardarUsuario(Usuario usuario) {
        byte[] bytesEncoded = Base64.getEncoder().encode((usuario.getEmail() + usuario.getContrasenia()).getBytes());
        String token = new String(bytesEncoded);
        UsuarioOAuth user = null;
        if (usuarios.get(token) != null) {
            user = new UsuarioOAuth(usuario, new Date(), token);
            usuarios.put(token, user);
        }

        return user;
    }

    public Administrador CrearAdministrador(String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono, Integer puntuacion,
            String email, String contrasenia, String direccion) {
        Administrador administrador = new Administrador(null, telefono, nombre, email, contrasenia, direccion, fechaNacimiento);
        em.persist(administrador);
        return administrador;
    }

    public Jugador CrearJugador(String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono, Integer puntuacion,
            String email, String contrasenia, String direccion) {
        Jugador jugador = new Jugador(nombre, fechaNacimiento, equipos, telefono, puntuacion, email, contrasenia, direccion);
        em.persist(jugador);
        return jugador;
    }

    public List<Administrador> ListarAdministradores() {
        return em.createQuery("select a from Administrador a").getResultList();
    }

    public List<Jugador> ListarJugadores() {
        return em.createQuery("select j from Jugador j").getResultList();
    }

    public UsuarioOAuth IniciarSesionAdministrador(String email, String contrasenia) {
        Administrador admin = (Administrador) em.createQuery("select a from Administrador a where a.email = :email and a.contrasenia = :contrasenia")
                .setParameter("email", email)
                .setParameter("contrasenia", contrasenia)
                .getSingleResult();
        UsuarioOAuth user = null;
        if (admin != null) {
            user = guardarUsuario(admin);
        }

        return user;
    }

    public UsuarioOAuth IniciarSesionJugador(String email, String contrasenia) {
        Jugador jugador = (Jugador) em.createQuery("select j from Jugador j where j.email = :email and j.contrasenia = :contrasenia")
                .setParameter("email", email)
                .setParameter("contrasenia", contrasenia)
                .getSingleResult();
        UsuarioOAuth user = null;
        if (jugador != null) {
            user = guardarUsuario(jugador);
        }

        return user;
    }

    public UsuarioOAuth buscarUsuario(String token) {
        return usuarios.get(token);
    }

    public boolean esAdministradorDelLocal(Administrador administrador, Partido partido) {
        for (Administrador adm : partido.getCancha().getLocal().getAdministradores()) {
            if (adm.equals(administrador)) {
                return true;
            }
        }
        return false;
    }

    public Jugador buscarJugador(Long id) {
        return (Jugador) em.createQuery("select j from Jugador j where j.id = :id")
                .setParameter("id", id).getSingleResult();
    }

}
