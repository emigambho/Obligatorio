package taller.sb;

import com.sun.xml.ws.security.impl.policy.Constants;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import taller.entidad.Servicio;
import taller.entidad.Solicitud;

@Stateless
public class ServicioBean {
    
    @Resource(lookup = "jms/ServicioQueue")
    private Queue queue;
    
    @Resource(lookup = "jms/ServicioQueueFactory")
    private QueueConnectionFactory connectionFactory;
    
    @EJB
    private SistemaBean sistemaBean;
    
    public Solicitud agregarServicio(Long idSolicitud, String servicio) {
        Solicitud solicitud = sistemaBean.buscarSolicitudPorId(idSolicitud);
        if (solicitud != null) {
            solicitud.addToServicios(new Servicio(servicio, "PENDIENTE"));
        }
        return solicitud;
    }
    
    public void registrarCambioEstado(Long idSolicitud, String servicio, String estado) {
        
        try {
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            QueueSender sender = session.createSender(queue);
            MapMessage message = session.createMapMessage();
            message.setLong("idSolicitud", idSolicitud);
            message.setString("servicio", servicio);
            message.setString("estado", estado);
            sender.send(message);
            session.close();
            connection.close();
        } catch (Exception ex) {
            
        }
        
    }
    
    public void cambiarEstado(Long idSolicitud, String servicio, String estado) {
        System.out.println("Cambiando estado async!");
        Solicitud solicitud = sistemaBean.buscarSolicitudPorId(idSolicitud);
        if (solicitud != null) {
            for (Servicio s : solicitud.getServicios()) {
                if (s.getDescripcion().equalsIgnoreCase(servicio)) {
                    s.setEstado(estado);
                }
            }
        }
        System.out.println("Fin cambio estado!");
    }

}