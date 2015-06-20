package MessageDrivenBean;

import SessionBean.PartidoBean;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import entidad.Equipo;
import entidad.Jugador;
import java.util.Date;

@MessageDriven(mappedName = "PartidoQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class PartidoMDB implements MessageListener {
    
    @EJB
    private PartidoBean partidoBean;
    
    public PartidoMDB() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;
                String fecha = msg.getString("fecha");
                Boolean esJugador = msg.getBoolean("jugador");
                Long localId = msg.getLong("localId");
  
                if(!esJugador){
                    Long equipoId = msg.getLong("equipoId");
                    partidoBean.registrarEquipoPartidoAutomatico(fecha, equipoId, localId);
                } else {
                    Long jugadorId = msg.getLong("jugadorId");
                    partidoBean.registrarJugadorPartidoAutomatico(fecha, jugadorId, localId);
                }
                
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }
    
}
