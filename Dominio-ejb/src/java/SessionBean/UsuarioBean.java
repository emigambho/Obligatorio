package SessionBean;

import entidad.Administrador;
import entidad.Cancha;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Local;
import entidad.Partido;
import java.util.Date;
import java.util.List;
import entidad.Usuario;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import partido.util.EstadoPartido;
import usuario.util.UsuarioOAuth;

@Singleton
public class UsuarioBean {

    @PersistenceContext
    EntityManager em;

    private Map<String, UsuarioOAuth> usuarios;

    public UsuarioBean() throws ParseException {
        
    }

    @PostConstruct
    private void startup() {
        try {
            this.usuarios = new HashMap<>();
            Timer timer = new Timer();
            timer.schedule(new LimpiarTimerTask(), 0, //initial delay
                    60000); //subsequent rate
            cargarDatos();
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private UsuarioOAuth guardarUsuario(Usuario usuario) {
        byte[] bytesEncoded = Base64.getEncoder().encode((usuario.getEmail() + usuario.getContrasenia()).getBytes());
        String token = new String(bytesEncoded);
        UsuarioOAuth user = null;
        if (usuarios.get(token) == null) {
            user = new UsuarioOAuth(usuario, new Date(), token);
            usuarios.put(token, user);
        }

        return user;
    }

    public void cargarDatos() throws ParseException{
        List<Cancha> canchasLocal1 = new ArrayList<>();
        List<Cancha> canchasLocal2 = new ArrayList<>();
        List<Cancha> canchasLocal3 = new ArrayList<>();

        List<Local> localesAdministrador1 = new ArrayList<>();
        List<Local> localesAdministrador2 = new ArrayList<>();
        List<Local> localesAdministrador3 = new ArrayList<>();
        List<Local> localesAdministrador4 = new ArrayList<>();

        List<Partido> partidosCancha1 = new ArrayList<>();
        List<Partido> partidosCancha2 = new ArrayList<>();
        List<Partido> partidosCancha3 = new ArrayList<>();
        List<Partido> partidosCancha4 = new ArrayList<>();
        List<Partido> partidosCancha5 = new ArrayList<>();
        List<Partido> partidosCancha6 = new ArrayList<>();

        List<Equipo> equiposJugador1 = new ArrayList<>();
        List<Equipo> equiposJugador2 = new ArrayList<>();
        List<Equipo> equiposJugador3 = new ArrayList<>();
        List<Equipo> equiposJugador4 = new ArrayList<>();
        List<Equipo> equiposJugador5 = new ArrayList<>();
        List<Equipo> equiposJugador6 = new ArrayList<>();
        List<Equipo> equiposJugador7 = new ArrayList<>();
        List<Equipo> equiposJugador8 = new ArrayList<>();
        List<Equipo> equiposJugador9 = new ArrayList<>();
        List<Equipo> equiposJugador10 = new ArrayList<>();
        List<Equipo> equiposJugador11 = new ArrayList<>();
        List<Equipo> equiposJugador12 = new ArrayList<>();
        List<Equipo> equiposJugador13 = new ArrayList<>();
        List<Equipo> equiposJugador14 = new ArrayList<>();
        List<Equipo> equiposJugador15 = new ArrayList<>();
        List<Equipo> equiposJugador16 = new ArrayList<>();
        List<Equipo> equiposJugador17 = new ArrayList<>();
        List<Equipo> equiposJugador18 = new ArrayList<>();
        List<Equipo> equiposJugador19 = new ArrayList<>();
        List<Equipo> equiposJugador20 = new ArrayList<>();
        List<Equipo> equiposJugador21 = new ArrayList<>();

        List<Jugador> jugadoresEquipo1 = new ArrayList<>();
        List<Jugador> jugadoresEquipo2 = new ArrayList<>();
        List<Jugador> jugadoresEquipo3 = new ArrayList<>();
        List<Jugador> jugadoresEquipo4 = new ArrayList<>();
        List<Jugador> jugadoresEquipo5 = new ArrayList<>();

        List<Partido> partidosEquipo1 = new ArrayList<>();
        List<Partido> partidosEquipo2 = new ArrayList<>();
        List<Partido> partidosEquipo3 = new ArrayList<>();
        List<Partido> partidosEquipo4 = new ArrayList<>();
        List<Partido> partidosEquipo5 = new ArrayList<>();

        List<Equipo> equiposPartido1 = new ArrayList<>(2);
        List<Equipo> equiposPartido2 = new ArrayList<>(2);
        List<Equipo> equiposPartido3 = new ArrayList<>(2);
        List<Equipo> equiposPartido4 = new ArrayList<>(2);
        List<Equipo> equiposPartido5 = new ArrayList<>(2);
        List<Equipo> equiposPartido6 = new ArrayList<>(2);
        List<Equipo> equiposPartido7 = new ArrayList<>(2);
        List<Equipo> equiposPartido8 = new ArrayList<>(2);
        List<Equipo> equiposPartido9 = new ArrayList<>(2);
        List<Equipo> equiposPartido10 = new ArrayList<>(2);

        Cancha cancha1 = new Cancha("5", 20.10, partidosCancha1, true);
        Cancha cancha2 = new Cancha("5", 20.10, partidosCancha2, true);
        Cancha cancha3 = new Cancha("5", 20.10, partidosCancha3, true);
        Cancha cancha4 = new Cancha("5", 20.10, partidosCancha4, true);
        Cancha cancha5 = new Cancha("5", 20.10, partidosCancha5, true);
        Cancha cancha6 = new Cancha("5", 20.10, partidosCancha6, true);

        canchasLocal1.add(cancha1);
        canchasLocal1.add(cancha2);
        canchasLocal2.add(cancha3);
        canchasLocal2.add(cancha4);
        canchasLocal3.add(cancha5);
        canchasLocal3.add(cancha6);

        Local local1 = new Local("Soriano 1122", canchasLocal1, 254321, 8, 22);
        Local local2 = new Local("Tacuarembo 2222", canchasLocal2, 278312, 10, 22);
        Local local3 = new Local("Galicia 1432", canchasLocal3, 324321, 9, 23);

        localesAdministrador1.add(local3);
        localesAdministrador1.add(local1);
        localesAdministrador2.add(local2);
        localesAdministrador2.add(local3);
        localesAdministrador3.add(local2);
        localesAdministrador3.add(local1);
        localesAdministrador4.add(local1);
        localesAdministrador4.add(local2);
        localesAdministrador4.add(local3);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String dateInString = "1989-08-09";
        Administrador administrador1 = new Administrador(localesAdministrador1, 2543113, "Administrador1", "asda@gmail.com", "asda2", "Paysandu 2222", sdf.parse(dateInString));
        dateInString = "1990-02-02";
        Administrador administrador2 = new Administrador(localesAdministrador2, 2715234, "Administrador2", "ghj@gmail.com", "uye3", "Mercedes 874", sdf.parse(dateInString));
        dateInString = "1989-11-09";
        Administrador administrador3 = new Administrador(localesAdministrador3, 2733234, "Administrador3", "bnm@gmail.com", "iop9", "Rio Negro 874", sdf.parse(dateInString));
        dateInString = "1988-08-19";
        Administrador administrador4 = new Administrador(localesAdministrador4, 2799234, "Administrador4", "qwe@gmail.com", "azx1", "18 de Julio 874", sdf.parse(dateInString));

        Equipo equipo1 = new Equipo(jugadoresEquipo1, 4, "Azul", partidosEquipo1, "EquipoA");
        Equipo equipo2 = new Equipo(jugadoresEquipo2, 5, "Amarrillo", partidosEquipo2, "EquipoA");
        Equipo equipo3 = new Equipo(jugadoresEquipo3, 6, "Rojo", partidosEquipo3, "EquipoA");
        Equipo equipo4 = new Equipo(jugadoresEquipo4, 7, "Negro", partidosEquipo4, "EquipoA");
        Equipo equipo5 = new Equipo(jugadoresEquipo5, 3, "Gris", partidosEquipo5, "EquipoA");

        dateInString = "1990-02-02";
        Jugador jugador1 = new Jugador("Jugador1", sdf.parse(dateInString), equiposJugador1, 24002134, 3, "qwe1@gmail.com", "tre11", "Paysandu 456");
        dateInString = "1990-02-02";
        Jugador jugador2 = new Jugador("Jugador2", sdf.parse(dateInString), equiposJugador2, 24222134, 4, "qwe2@gmail.com", "tre12", "Berro 456");
        dateInString = "1990-02-02";
        Jugador jugador3 = new Jugador("Jugador3", sdf.parse(dateInString), equiposJugador3, 24112134, 5, "qwe3@gmail.com", "tre13", "Magallanes 456");
        dateInString = "1990-02-02";
        Jugador jugador4 = new Jugador("Jugador4", sdf.parse(dateInString), equiposJugador4, 24442134, 6, "qwe4@gmail.com", "tre14", "Goes 456");
        dateInString = "1990-02-02";
        Jugador jugador5 = new Jugador("Jugador5", sdf.parse(dateInString), equiposJugador5, 24552134, 7, "qwe5@gmail.com", "tre15", "Acevedo 456");
//        dateInString = "02-02-1990";
        Jugador jugador6 = new Jugador("Jugador6", sdf.parse(dateInString), equiposJugador6, 24662134, 8, "qwe6@gmail.com", "tre16", "Maldonado 456");
//        dateInString = "02-02-1990";
        Jugador jugador7 = new Jugador("Jugador7", sdf.parse(dateInString), equiposJugador7, 24772134, 9, "qwe7@gmail.com", "tre17", "Canelones 456");
//        dateInString = "02-02-1990";
        Jugador jugador8 = new Jugador("Jugador8", sdf.parse(dateInString), equiposJugador8, 24882134, 10, "qwe8@gmail.com", "tre18", "Yi 456");
//        dateInString = "02-02-1990";
        Jugador jugador9 = new Jugador("Jugador9", sdf.parse(dateInString), equiposJugador9, 24992134, 1, "qwe9@gmail.com", "tre91", "Yaguaron 456");
//        dateInString = "02-02-1990";
        Jugador jugador10 = new Jugador("Jugador10", sdf.parse(dateInString), equiposJugador10, 24002111, 2, "qwe10@gmail.com", "tre10", "Ejido 456");
//        dateInString = "02-02-1990";
        Jugador jugador11 = new Jugador("Jugador11", sdf.parse(dateInString), equiposJugador11, 24002122, 3, "qwe11@gmail.com", "tre11", "Paraguay 456");
//        dateInString = "02-02-1990";
        Jugador jugador12 = new Jugador("Jugador12", sdf.parse(dateInString), equiposJugador12, 24002133, 4, "qwe12@gmail.com", "tre111", "Colonia 456");
//        dateInString = "02-02-1990";
        Jugador jugador13 = new Jugador("Jugador13", sdf.parse(dateInString), equiposJugador13, 24002144, 5, "qwe14@gmail.com", "tre122", "Vazquez 456");
//        dateInString = "02-02-1990";
        Jugador jugador14 = new Jugador("Jugador14", sdf.parse(dateInString), equiposJugador14, 24002155, 6, "qwe15@gmail.com", "tre133", "Tacuarembo 456");
//        dateInString = "02-02-1990";
        Jugador jugador15 = new Jugador("Jugador15", sdf.parse(dateInString), equiposJugador15, 24002166, 7, "qwe16@gmail.com", "tre11111", "Salto 456");
//        dateInString = "02-02-1990";
        Jugador jugador16 = new Jugador("Jugador16", sdf.parse(dateInString), equiposJugador16, 24002177, 8, "qwe17@gmail.com", "tre1ads", "Av Italia 456");
//        dateInString = "02-02-1990";
        Jugador jugador17 = new Jugador("Jugador17", sdf.parse(dateInString), equiposJugador17, 24002188, 9, "qwe18@gmail.com", "tre1asv", "Rivera 456");
//        dateInString = "02-02-1990";
        Jugador jugador18 = new Jugador("Jugador18", sdf.parse(dateInString), equiposJugador18, 24002199, 9, "qwe19@gmail.com", "tre1cc", "Chana 456");
//        dateInString = "02-02-1990";
        Jugador jugador19 = new Jugador("Jugador19", sdf.parse(dateInString), equiposJugador19, 24002134, 6, "qwe20@gmail.com", "tre1aw", "Guana 456");
//        dateInString = "02-02-1990";
        Jugador jugador20 = new Jugador("Jugador20", sdf.parse(dateInString), equiposJugador20, 23302134, 2, "qwe21@gmail.com", "tre1mm", "Gaboto 456");
//        dateInString = "02-02-1990";
        Jugador jugador21 = new Jugador("Jugador21", sdf.parse(dateInString), equiposJugador21, 22202134, 3, "qwe22@gmail.com", "tre1qq", "26 de Marzo 456");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dateInString1 = "2015-01-07 15:00:00";
        String dateInString2 = "2015-01-07 14:00:00";
        Partido partido1 = new Partido(equiposPartido1, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-02-07 15:00:00";
        dateInString2 = "2015-02-07 14:00:00";
        Partido partido2 = new Partido(equiposPartido2, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-03-07 15:00:00";
        dateInString2 = "2015-03-07 14:00:00";
        Partido partido3 = new Partido(equiposPartido3, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-04-07 15:00:00";
        dateInString2 = "2015-04-07 14:00:00";
        Partido partido4 = new Partido(equiposPartido4, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-05-07 15:00:00";
        dateInString2 = "2015-05-07 14:00:00";
        Partido partido5 = new Partido(equiposPartido5, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-06-07 15:00:00";
        dateInString2 = "2015-06-07 14:00:00";
        Partido partido6 = new Partido(equiposPartido6, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-07-07 15:00:00";
        dateInString2 = "2015-07-07 14:00:00";
        Partido partido7 = new Partido(equiposPartido7, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-08-07 15:00:00";
        dateInString2 = "2015-08-07 14:00:00";
        Partido partido8 = new Partido(equiposPartido8, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-09-07 15:00:00";
        dateInString2 = "2015-09-07 14:00:00";
        Partido partido9 = new Partido(equiposPartido9, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);
        dateInString1 = "2015-10-07 15:00:00";
        dateInString2 = "2015-10-07 14:00:00";
        Partido partido10 = new Partido(equiposPartido10, sdf1.parse(dateInString2), sdf1.parse(dateInString1), EstadoPartido.RESERVADO);

        jugadoresEquipo1.add(jugador1);
        jugadoresEquipo1.add(jugador2);
        jugadoresEquipo1.add(jugador3);
        jugadoresEquipo1.add(jugador4);
        jugadoresEquipo1.add(jugador5);
        jugadoresEquipo2.add(jugador6);
        jugadoresEquipo2.add(jugador7);
        jugadoresEquipo2.add(jugador8);
        jugadoresEquipo2.add(jugador9);
        jugadoresEquipo2.add(jugador10);
        jugadoresEquipo3.add(jugador11);
        jugadoresEquipo3.add(jugador12);
        jugadoresEquipo3.add(jugador13);
        jugadoresEquipo3.add(jugador14);
        jugadoresEquipo3.add(jugador15);
        jugadoresEquipo4.add(jugador16);
        jugadoresEquipo4.add(jugador17);
        jugadoresEquipo4.add(jugador18);
        jugadoresEquipo4.add(jugador19);
        jugadoresEquipo4.add(jugador20);
        jugadoresEquipo4.add(jugador20);
        
        equiposJugador1.add(equipo1);
        equiposJugador2.add(equipo1);
        equiposJugador3.add(equipo1);
        equiposJugador4.add(equipo1);
        equiposJugador5.add(equipo1);
        equiposJugador6.add(equipo2);
        equiposJugador7.add(equipo2);
        equiposJugador8.add(equipo2);
        equiposJugador9.add(equipo2);
        equiposJugador10.add(equipo2);
        equiposJugador11.add(equipo3);
        equiposJugador12.add(equipo3);
        equiposJugador13.add(equipo3);
        equiposJugador14.add(equipo3);
        equiposJugador15.add(equipo3);
        equiposJugador16.add(equipo4);
        equiposJugador17.add(equipo4);
        equiposJugador18.add(equipo4);
        equiposJugador19.add(equipo4);
        equiposJugador20.add(equipo4);
        equiposJugador21.add(equipo4);
        

        partidosEquipo1.add(partido1);
        partidosEquipo1.add(partido2);
        partidosEquipo1.add(partido6);
        partidosEquipo1.add(partido9);

        partidosEquipo2.add(partido1);
        partidosEquipo2.add(partido3);
        partidosEquipo2.add(partido7);
         partidosEquipo2.add(partido8);

        partidosEquipo3.add(partido3);
        partidosEquipo3.add(partido4);
        partidosEquipo3.add(partido6);
        partidosEquipo3.add(partido10);

        partidosEquipo4.add(partido2);
        partidosEquipo4.add(partido5);
        partidosEquipo4.add(partido7);
        partidosEquipo4.add(partido10);

        partidosEquipo5.add(partido4);
        partidosEquipo5.add(partido5);
        partidosEquipo5.add(partido8);
        partidosEquipo5.add(partido9);

        em.persist(administrador1);
        em.persist(administrador2);
        em.persist(administrador3);
        em.persist(administrador4);
        em.persist(local1);
        em.persist(local2);
        em.persist(local3);

    }
    
    public Administrador CrearAdministrador(List<Local> locales, Integer telefono, String nombre, String email, String contrasenia, String direccion, Date fechaNacimiento){
        Administrador administrador = new Administrador(null, telefono, nombre, email, contrasenia, direccion, fechaNacimiento);
        em.persist(administrador);
        return administrador;
    }

    public Jugador CrearJugador(String nombre, Date fechaNacimiento, List<Equipo> equipos, Integer telefono, Integer puntuacion,
            String email, String contrasenia, String direccion){
        Jugador jugador = new Jugador(nombre, fechaNacimiento, equipos, telefono, puntuacion, email, contrasenia, direccion);
        em.persist(jugador);
        return jugador;
    }
    
    public List<Usuario> ListarUsuario() {
        return em.createQuery("select u from Usuario u").getResultList();
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

    public Administrador buscarAdminstradorId(Long idAdministrador) {
        return (Administrador) em.createQuery("select a from Administrador e where a.id = :id")
                .setParameter("id", idAdministrador).getSingleResult();
    }
    
     public Jugador buscarJugadorId(Long idJugador) {
        return (Jugador) em.createQuery("select j from Jugador j where j.id = :id")
                .setParameter("id", idJugador).getSingleResult();
    }

    public boolean esAdministradorDelLocal(Administrador administrador, Partido partido) {
        for (Administrador adm : partido.getCancha().getLocal().getAdministradores()) {
            if (adm.equals(administrador)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, UsuarioOAuth> getUsuarios() {
        return usuarios;
    }

    public Jugador buscarJugador(Long id) {
        return (Jugador) em.createQuery("select j from Jugador j where j.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    class LimpiarTimerTask extends TimerTask {

        @Override
        public void run() {
            List<UsuarioOAuth> usuariosList = new ArrayList<UsuarioOAuth>(usuarios.values());
            for (UsuarioOAuth user : usuariosList) {
                Date fechaActual = new Date();
                if (fechaActual.compareTo(user.getActivoHasta()) >= 0) {
                    usuarios.remove(user.getToken());
                }
            }

        }
    }
}
