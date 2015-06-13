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

@MessageDriven(mappedName = "jms/PartidoQueue", activationConfig = {
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
                Date fecha = (Date)msg.getObject("fecha");
                Jugador jugador = (Jugador)msg.getObject("jugador");
                Equipo equipo = (Equipo)msg.getObject("equipo");
                
                if(equipo != null){
                    partidoBean.registrarEquipo(fecha, equipo);
                } else {
                    partidoBean.registrarJugador(fecha, jugador);
                }
                
            }
        } catch (Exception ex) {
            
        }
    }
    
}
