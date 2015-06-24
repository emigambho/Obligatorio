package messagedrivenbean;

import exception.PartidoException;
import sessionbean.PartidoBean;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(mappedName = "PartidoQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class PartidoMdb implements MessageListener {

    @EJB
    private PartidoBean partidoBean;

    public PartidoMdb() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof MapMessage) {
                MapMessage msg = (MapMessage) message;
                String fecha = msg.getString("fecha");
                Boolean esJugador = msg.getBoolean("jugador");
                Long localId = msg.getLong("localId");

                if (!esJugador) {
                    Long equipoId = msg.getLong("equipoId");
                    partidoBean.registrarEquipoPartidoAutomatico(fecha, equipoId, localId);
                } else {
                    Long jugadorId = msg.getLong("jugadorId");
                    partidoBean.registrarJugadorPartidoAutomatico(fecha, jugadorId, localId);
                }

            }
        } catch (JMSException | PartidoException | ParseException ex) {
            Logger.getLogger(PartidoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
