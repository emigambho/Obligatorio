package SessionBean;

import entidad.Administrador;
import entidad.Cancha;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Local;
import entidad.Partido;
import exception.PartidoException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import partido.util.EstadoPartido;

@Stateless
public class PartidoBean {
    @Resource(name = "Mail")
    private javax.mail.Session mail;

    @EJB
    public EquipoBean equipoBean;

    @EJB
    public UsuarioBean usuarioBean;

    @Resource(lookup = "PartidoQueue")
    private Queue colaDePartidos;

    @Resource(lookup = "PartidoQueueFactory")
    private QueueConnectionFactory connectionFactory;
    
    private Map<Long,Local> localesMap;

    @PersistenceContext
    private EntityManager em;

    public Partido crearPartido(Long idEquipoA, Long idEquipoB, Date fechaF, Date fechaI, EstadoPartido estado, Administrador administrador){
        Equipo equipoA = equipoBean.buscarEquipo(idEquipoA);
        Equipo equipoB = equipoBean.buscarEquipo(idEquipoB);
        List<Equipo> equipos = new ArrayList<>(2);
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
    
    public Equipo buscarEquipo(Long idEquipo) {
        return (Equipo) em.createQuery("select e from Equipo e where e.id = :idEquipo")
                .setParameter("idEquipo", idEquipo).getSingleResult();
    }

    public Local buscarLocal(Long localId) {
        return (Local) em.createQuery("select l from Local l where l.id = :localId")
                .setParameter("localId", localId).getSingleResult();
    }
    
    public List<Equipo> ObtenerEquiposPartido(Long idPartido) {
        Partido partido = buscarPartido(idPartido);
        List<Equipo> equipos = new ArrayList<>();
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
            puntuacionA += 2;
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

    public void registrarJugadorAPartido(String fecha, Jugador jugador, Long localId) {
        try {
            
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(colaDePartidos);
            MapMessage message = session.createMapMessage();
            message.setString("fecha", fecha);
            message.setBoolean("jugador", true);
            message.setLong("jugadorId", jugador.getId());
            message.setLong("localId", localId);
            sender.send(message);
            session.close();
            connection.close();
        } catch (Exception ex) {

        }
    }

    public void registrarEquipoAPartido(String fecha, Long equipoId, Long localId) {
        //TODO: Realizar bien las configuraciones
        try {
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(colaDePartidos);
            MapMessage message = session.createMapMessage();
            message.setString("fecha", fecha);
            message.setBoolean("jugador", false);
            message.setLong("equipoId", equipoId);
            message.setLong("localId", localId);
            sender.send(message);
            session.close();
            connection.close();
        } catch (Exception ex) {

        }
    }

    public void registrarJugadorPartidoAutomatico(String fecha, Long jugadorId, Long localId) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
            Date fechaDelPartido = sdf.parse(fecha);
            Jugador jugador = usuarioBean.buscarJugador(jugadorId);
            if(jugador == null){
                // Devolver error, Jugador no encontrado
            }
            System.out.println("Registrar jugador de nombre " + jugador.getNombre() +" a un partido automatico con fecha" + fechaDelPartido);
            Local local = localesMap.get(localId);
            if(local == null){
                local = buscarLocal(localId);
            }
            if(local==null){
                //Devolver error, local no encontrado
            }
            
            for(Cancha cancha : local.getCanchas()){
                cancha.getPartidos();
            }
            
        } catch (ParseException ex) {
            Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void registrarEquipoPartidoAutomatico(String fecha, Long equipoId, Long localId) throws PartidoException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy hh:mm:ss");
        Date fechaDelPartido = sdf.parse(fecha);
        Equipo equipo = buscarEquipo(equipoId);
        if(equipo == null){
            throw new PartidoException();// Devolver error, Equipo no existe
        }
        System.out.println("Registrar jugador de nombre " + equipo.getNombre() +" a un partido automatico con fecha" + fechaDelPartido);

        Local local = localesMap.get(localId);
        if(local == null){
            local = buscarLocal(localId);
        }  
        if(local==null){
            throw new PartidoException();//Devolver error, local no encontrado
        }
        localesMap.put(localId, local);
        Boolean partidoEncontrado = false;
        for(Cancha cancha : local.getCanchas()){
            Boolean partidoCompleto = false;
            for(Partido partido :cancha.getPartidos()){
                if(partido.getFechaI().compareTo(fechaDelPartido)==0){
                    if(partido.getEquipos().size()<=1){
                        partido.getEquipos().add(equipo);
                        partidoEncontrado=true;      
                    } else {
                        partidoCompleto = true;
                    }
                    break;
                } 
            }
            
            if(partidoEncontrado){
                break;
            } else{
                if(!partidoCompleto){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaDelPartido);
                    calendar.add(Calendar.HOUR, 1);
                    crearPartido(equipoId, null, calendar.getTime(), fechaDelPartido,
                        EstadoPartido.RESERVADO, local.getAdministradores().get(0));
                }  
            }
        }  
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

    public Map<Long, Local> getLocalesMap() {
        if(localesMap == null)
            localesMap = new HashMap<>();
        return localesMap;
    }

    public void setLocalesMap(Map<Long, Local> localesMap) {
        this.localesMap = localesMap;
    }

    private void sendMail(String email, String subject, String body) throws NamingException, MessagingException {
        MimeMessage message = new MimeMessage(mail);
        message.setSubject(subject);
        message.setFrom("noreplay@obligatorio.com");
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
        message.setText(body);
        Transport.send(message);
    }
            
    
}
