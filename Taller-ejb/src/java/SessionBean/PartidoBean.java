package SessionBean;

import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
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
    
    public List<Partido> buscarClientePorNombre(String nombre) {
        return null;
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