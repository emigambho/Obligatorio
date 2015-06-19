package SessionBean;

import entidad.Administrador;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
import exception.PartidoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import partido.util.EstadoPartido;

@Stateless
public class PartidoBean {

    @EJB
    public EquipoBean equipoBean;

    @EJB
    public UsuarioBean usuarioBean;

    @Resource(lookup = "PartidoQueue")
    private Queue colaDePartidos;

    @Resource(lookup = "PartidoQueueFactory")
    private QueueConnectionFactory connectionFactory;

    @PersistenceContext
    EntityManager em;

    public Partido crearPartido(Long idEquipoA, Long idEquipoB, Date fechaF, Date fechaI, EstadoPartido estado, Administrador administrador){
        Equipo equipoA = equipoBean.buscarEquipo(idEquipoA);
        Equipo equipoB = equipoBean.buscarEquipo(idEquipoB);
        List<Equipo> equipos = new ArrayList<Equipo>(2);
        if (equipoB != null && equipoA != null) {
            if (fechaI.compareTo(fechaF) >= 0) {
                equipos.add(equipoA);
                equipos.add(equipoB);
                Partido partido = new Partido(equipos, fechaF, fechaI, estado);
                if (usuarioBean.esAdministradorDelLocal(administrador, partido)) {
                    partido.setEsadoParido(EstadoPartido.RESERVADO);
                    em.persist(partido);
                    return partido;
                }
            }
        }
        return null;
    }

    public void cancelarPartido(Long idPartido, Administrador administrador) {
        Partido partido = buscarPartido(idPartido);
        if (partido != null && EstadoPartido.RESERVADO.equals(partido.getEstado())) {
            if (usuarioBean.esAdministradorDelLocal(administrador, partido)) {
                partido.setEsadoParido(EstadoPartido.CANCELADO);
                em.persist(partido);
            }
        }
    }

    public void terminarPartido(Long idPartido, Date fechaFin, Integer golesEquipoA, Integer golesEquipoB, Administrador administrador) throws PartidoException {
        Partido partido = buscarPartido(idPartido);
        Date fechaActual = new Date();
        if (partido != null) {
            if (fechaActual.compareTo(fechaFin) >= 0) {
                if (usuarioBean.esAdministradorDelLocal(administrador, partido)) {
                    partido.setEsadoParido(EstadoPartido.TERMINADO);
                    partido.setGolesA(golesEquipoA);
                    partido.setGolesB(golesEquipoB);
                    em.persist(partido);
                }
            } else {
                throw new PartidoException("Partido.error.1.1","Fecha acual es mayor a fecha fin");
            }
        }
    }

    public void confirmarPartido(Long idPartido, Date fechaInicio, Administrador administrador) throws PartidoException {
        Partido partido = buscarPartido(idPartido);
        Date fechaActual = new Date();
        if (partido != null) {
            if (fechaActual.compareTo(fechaInicio) < 0) {
                if (partido.getEquipos().size() == 2) {
                    if (partido.getEquipos().get(0).getJugadores().size() == 5) {
                        if (partido.getEquipos().get(1).getJugadores().size() == 5) {
                            if (usuarioBean.esAdministradorDelLocal(administrador, partido)) {
                                partido.setEsadoParido(EstadoPartido.CONFIRMADO);
                            }
                        } else {
                            throw new PartidoException("Partido.error.2.2","Equipo B no llega a 5 jugadores");
                        }
                    } else {
                        throw new PartidoException("Partido.error.2.1","Equipo A no llega a 5 jugadores");
                    }
                }
            } else {
                throw new PartidoException("Partido.error.1.2","Fecha acual es mayor a fecha Inicio");
            }
        }
    }

    public Partido actualizarPartido(Partido partido) {
        em.refresh(partido);
        return partido;
    }

    public List<Partido> listarPartidos() {
        return em.createQuery("select p from Partido p").getResultList();
    }

    public Partido buscarPartido(Long idPartido) {
        return (Partido) em.createQuery("select p from Partido p where p.id = :idPartido")
                .setParameter("idPartido", idPartido).getSingleResult();
    }

    public List<Equipo> ObtenerEquiposPartido(Long idPartido) {
        Partido partido = buscarPartido(idPartido);
        List<Equipo> equipos = new ArrayList<Equipo>();
        equipos.add(partido.getEquipos().get(0));
        equipos.add(partido.getEquipos().get(1));
        return equipos;
    }

    public void terminarPartido(Long idPartido, Integer golesA, Integer golesB) {
        Partido partido = buscarPartido(idPartido);
        partido.setGolesA(golesA);
        partido.setGolesB(golesB);
        ActualizarCalsificaciones(partido);
        partido.setEsadoParido(EstadoPartido.TERMINADO);
        em.persist(partido);
    }

    public void ActualizarCalsificacionesJugadoresEmpate(Equipo equipo) {
        for (Jugador jugador : equipo.getJugadores()) {
            Integer puntuacion = jugador.getPuntuacion();
            if (puntuacion < 10) {
                puntuacion++;
            }
            jugador.setPuntuacion(puntuacion);
        }
    }

    public void ActualizarCalsificacionesEquiposEmpate(Integer puntuacionA, Integer puntuacionB) {
        if (puntuacionA < 10) {
            puntuacionA++;
        }
        if (puntuacionB < 10) {
            puntuacionB++;
        }
    }

    public void ActualizarCalsificacionesJugadoresGanaUnEquipo(Equipo equipoA,Equipo equipoB) {
        for (Jugador jugador : equipoA.getJugadores()) {
            Integer puntuacion = jugador.getPuntuacion();
            if (puntuacion < 9) {
                puntuacion = puntuacion + 2;
            } else if (puntuacion == 9) {
                puntuacion++;
            }
            jugador.setPuntuacion(puntuacion);
        }
        for(Jugador jugador : equipoB.getJugadores()){
            Integer puntuacion = jugador.getPuntuacion();
            if(puntuacion>0){
                puntuacion--;
            }
            jugador.setPuntuacion(puntuacion);
        }
    }

    public void ActualizarCalsificacionesEquiposGanaUnEquipo(Integer puntuacionA, Integer puntuacionB) {
        if (puntuacionA < 9) {
            puntuacionA = puntuacionA + 2;
        } else if (puntuacionA == 9) {
            puntuacionA++;
        }
        if (puntuacionB > 0) {
            puntuacionB--;
        }
    }

    public void ActualizarCalsificaciones(Partido partido) {
        Equipo equipoA = partido.getEquipos().get(0);
        Equipo equipoB = partido.getEquipos().get(1);
        Integer clasificacionEquipoA = equipoA.getClasificacion();
        Integer clasificacionEquipoB = equipoB.getClasificacion();
        Integer golesEquipoA = partido.getGolesA();
        Integer golesEquipoB = partido.getGolesB();
        //SI EMPATAN 1 PUNTO PARA CADA UNO, AL MENOS QUE YA TENGAN 10 PUNTOS LOS EQUIPOS
        if (golesEquipoA.compareTo(golesEquipoB) == 0) {
            ActualizarCalsificacionesEquiposEmpate(clasificacionEquipoA, clasificacionEquipoB);
            ActualizarCalsificacionesJugadoresEmpate(equipoA);
            ActualizarCalsificacionesJugadoresEmpate(equipoB);
            // SI GANA EL PARTIDO "A", SON DOS PUNTOS PARA "A" Y -1 PARA "B", AL MENOS QUE "A" TENGA 10(NO SUMA) Y SI TIENE 9 PUNTOS 
            //SUMA SOLO 1 Y "B" SI TIENE PUNTAJE 0 NO RESTA, SI EL PARTIDO O GANA "B" IDEM CON LOS PUNTAJES CAMBIADOS
        } else if (golesEquipoA.compareTo(golesEquipoB) > 0) {
            ActualizarCalsificacionesEquiposGanaUnEquipo(clasificacionEquipoA, clasificacionEquipoB);
            ActualizarCalsificacionesJugadoresGanaUnEquipo(equipoA,equipoB);
            // Falta restarle al los jugadores del equipo perdedor
        } else {
            ActualizarCalsificacionesEquiposGanaUnEquipo(clasificacionEquipoB, clasificacionEquipoA);
            ActualizarCalsificacionesJugadoresGanaUnEquipo(equipoB,equipoA);
            // Falta restarle al los jugadores del equipo perdedor
        }
        equipoA.setClasificacion(clasificacionEquipoA);
        equipoB.setClasificacion(clasificacionEquipoB);
    }

    public void registrarJugadorAPartido(Date fecha, Jugador jugador) {
        //TODO: Realizar bien las configuraciones
        try {
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(colaDePartidos);
            MapMessage message = session.createMapMessage();
            message.setObject("fecha", fecha);
            message.setObject("jugador", jugador);
            sender.send(message);
            session.close();
            connection.close();
        } catch (Exception ex) {

        }
    }

    public void registrarEquipoAPartido(Date fecha, Equipo equipo) {
        //TODO: Realizar bien las configuraciones
        try {
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(colaDePartidos);
            MapMessage message = session.createMapMessage();
            message.setObject("fecha", fecha);
            message.setObject("equipo", equipo);
            sender.send(message);
            session.close();
            connection.close();
        } catch (Exception ex) {

        }
    }

    public void registrarJugadorPartidoAutomatico(Date fecha, Jugador jugador) {
        System.out.println("Registrar jugador de nombre " + jugador.getNombre() +" a un partido automatico con fecha" + fecha);
        
    }

    public void registrarEquipoPartidoAutomatico(Date fecha, Equipo equipo) {
    }
    
    public EstadoPartido verEstadoPartido(Long idPartido){
        Partido partido = buscarPartido(idPartido);
        if(partido!=null){
            return partido.getEstado();
        }
        return null;
    }
    
    public Partido verInfoPartido(Long idPartido){
        Partido partido = buscarPartido(idPartido);
        if(partido!=null){
            return partido;
        }
        return null;
    }
            
}
