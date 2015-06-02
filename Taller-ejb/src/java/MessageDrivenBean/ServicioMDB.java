package MessageDrivenBean;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import SessionBean.ServicioBean;

@MessageDriven(mappedName = "jms/ServicioQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ServicioMDB implements MessageListener {
    
    @EJB
    private ServicioBean servicioBean;
    
    public ServicioMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;
                Long idSolicitud = msg.getLong("idSolicitud");
                String servicio = msg.getString("servicio");
                String estado = msg.getString("estado");
                servicioBean.cambiarEstado(idSolicitud, servicio, estado);
            }
        } catch (Exception ex) {
            
        }
    }
    
}
