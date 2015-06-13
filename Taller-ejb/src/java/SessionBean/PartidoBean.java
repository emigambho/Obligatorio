package SessionBean;

import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
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

@Stateless
public class PartidoBean {
     
    @Resource(lookup = "jms/PartidoQueue")
    private Queue colaDePartidos;
    
    @Resource(lookup = "jms/PartidoQueueFactory")
    private QueueConnectionFactory connectionFactory;

    @PersistenceContext
    EntityManager em;

    public Partido crearPartido(Partido partido) {
        em.persist(partido);
        return partido;
    }

    public Partido actualizarPartido(Partido partido) {
        em.refresh(partido);
        return partido;
    }

    public List<Partido> listarPartidos() {
        return em.createQuery("select p from Partido p").getResultList();
    }

    public Partido BuscarPartido(Long idPartido) {
        return (Partido) em.createQuery("select p from Partido p where p.id = :idPartido")
                .setParameter("idPartido", idPartido).getSingleResult();
    }

    public List<Equipo> ObtenerEquiposPartido(Long idPartido) {
        Partido partido = BuscarPartido(idPartido);
        List<Equipo> equipos = new ArrayList<Equipo>();
        equipos.add(partido.getEquipoB());
        equipos.add(partido.getEquipoA());
        return equipos;
    }

    public void ActualizarCalificaciones(Long idPartido) {
        Partido partido = BuscarPartido(idPartido);
        Equipo equipoA = partido.getEquipoA();
        Equipo equipoB = partido.getEquipoB();
        Integer clasificacionEquipoA = equipoA.getClasificacion();
        Integer clasificacionEquipoB = equipoB.getClasificacion();
        Integer golesEquipoA = partido.getGolesA();
        Integer golesEquipoB = partido.getGolesB();
       //SI EMPATAN 1 PUNTO PARA CADA UNO, AL MENOS QUE YA TENGAN 10 PUNTOS LOS EQUIPOS
        if (golesEquipoA.compareTo(golesEquipoB) == 0) {
            if (clasificacionEquipoA < 10) {
                clasificacionEquipoA++;
            }
            if (clasificacionEquipoB < 10) {
                clasificacionEquipoB++;
            }
        // SI GANA EL PARTIDO "A", SON DOS PUNTOS PARA "A" Y -1 PARA "B", AL MENOS QUE "A" TENGA 10(NO SUMA) Y SI TIENE 9 PUNTOS 
        //SUMA SOLO 1 Y "B" SI TIENE PUNTAJE 0 NO RESTA, SI EL PARTIDO O GANA "B" IDEM CON LOS PUNTAJES CAMBIADOS
        } else if (golesEquipoA.compareTo(golesEquipoB)>0) {
            if(clasificacionEquipoA<9){
                clasificacionEquipoA=clasificacionEquipoA+2;
            }else if(clasificacionEquipoA==9){
                clasificacionEquipoA++;
            }
            if(clasificacionEquipoB>0){
                clasificacionEquipoB--;
            }
        }else{
               if(clasificacionEquipoB<9){
                clasificacionEquipoB=clasificacionEquipoB+2;
            }else if(clasificacionEquipoB==9){
                clasificacionEquipoB++;
            }
            if(clasificacionEquipoA>0){
                clasificacionEquipoA--;
            }
        }
        equipoA.setClasificacion(clasificacionEquipoA);
        equipoB.setClasificacion(clasificacionEquipoB);
        //FALTARÍA GUARDAR LOS RESULTADOS EN LA BASE
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

    public void registrarJugador(Date fecha, Jugador jugador) {
        
    }

    public void registrarEquipo(Date fecha, Equipo equipo) {
    }
}
