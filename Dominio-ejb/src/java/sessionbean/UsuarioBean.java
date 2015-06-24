package sessionbean;

import entidad.Administrador;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Local;
import entidad.Partido;
import entidad.Usuario;
import usuario.util.UsuarioOAuth;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class UsuarioBean {

    @PersistenceContext
    EntityManager em;

    private Map<String, UsuarioOAuth> usuarios;

    public UsuarioBean() throws ParseException {

    }

    @PostConstruct
    private void startup() {
        this.usuarios = new HashMap<>();
        Timer timer = new Timer();
        timer.schedule(new LimpiarTimerTask(), 0, 60000);

    }

    private UsuarioOAuth guardarUsuario(Usuario usuario) {
        byte[] bytesEncoded = Base64.getEncoder().encode((usuario.getEmail() + usuario.getContrasenia()).getBytes());
        String token = new String(bytesEncoded);
        UsuarioOAuth user = usuarios.get(token);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        Date date = calendar.getTime();
        if (user == null) {
            user = new UsuarioOAuth(usuario, date, token);
            usuarios.put(token, user);
        } else {
            user.setActivoHasta(date);
        }

        return user;
    }

    public Administrador crearAdministrador(List<Local> locales, Integer telefono,
            String nombre, String email, String contrasenia, String direccion, Date fechaNacimiento) {
        Administrador administrador = new Administrador(null, telefono, nombre, email,
                contrasenia, direccion, fechaNacimiento);
        em.persist(administrador);
        return administrador;
    }

    public Jugador crearJugador(String nombre, Date fechaNacimiento, List<Equipo> equipos,
            Integer telefono, Integer puntuacion, String email, String contrasenia, String direccion) {
        Jugador jugador = new Jugador(nombre, fechaNacimiento, equipos, telefono,
                puntuacion, email, contrasenia, direccion);
        em.persist(jugador);
        return jugador;
    }

    public List<Administrador> listarAdministradores() {
        return em.createQuery("select a from Administrador a").getResultList();
    }

    public List<Jugador> listarJugadores() {
        return em.createQuery("select j from Jugador j").getResultList();
    }

    public UsuarioOAuth iniciarSesionAdministrador(String email, String contrasenia) {
        Administrador admin = (Administrador) 
                em.createQuery("select a from Administrador a where a.email = :email and a.contrasenia = :contrasenia")
                .setParameter("email", email)
                .setParameter("contrasenia", contrasenia)
                .getSingleResult();
        UsuarioOAuth user = null;
        if (admin != null) {
            user = guardarUsuario(admin);
        }

        return user;
    }

    public UsuarioOAuth iniciarSesionJugador(String email, String contrasenia) {
        Jugador jugador = (Jugador) 
                em.createQuery("select j from Jugador j where j.email = :email and j.contrasenia = :contrasenia")
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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 15);
        Date date = calendar.getTime();
        UsuarioOAuth user = usuarios.get(token);
        if (user != null) {
            user.setActivoHasta(date);
        }
        return user;
    }

    public Administrador buscarAdminstradorId(Long idAdministrador) {
        return (Administrador) em.createQuery("select a from Administrador a where a.id = :id")
                .setParameter("id", idAdministrador).getSingleResult();
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

    public Map<String, UsuarioOAuth> getUsuarios() {
        return usuarios;
    }

    class LimpiarTimerTask extends TimerTask {

        @Override
        public void run() {
            List<UsuarioOAuth> usuariosList = new ArrayList<>(usuarios.values());
            for (UsuarioOAuth user : usuariosList) {
                Date fechaActual = new Date();
                if (fechaActual.compareTo(user.getActivoHasta()) >= 0) {
                    usuarios.remove(user.getToken());
                }
            }
        }
    }
}
