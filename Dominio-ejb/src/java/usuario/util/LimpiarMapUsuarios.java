/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuario.util;

import SessionBean.UsuarioBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.ejb.EJB;

/**
 *
 * @author Usuario
 */
public class LimpiarMapUsuarios {

    @EJB
    UsuarioBean usuarioBean;

    Timer timer;

    public LimpiarMapUsuarios() {
        timer = new Timer();
        timer.schedule(new LimpiarTimerTask(), 0, //initial delay
            1 * 1000); //subsequent rate
    }

    
    class LimpiarTimerTask extends TimerTask {

        @Override
        public void run() {
            List<UsuarioOAuth> usuarios = new ArrayList<UsuarioOAuth>(usuarioBean.getUsuarios().values());       
            for (UsuarioOAuth user : usuarios) {
                Date fechaActual = new Date();
                if(fechaActual.compareTo(user.getActivoHasta())>=0){
                    usuarioBean.getUsuarios().remove(user.getToken());
                }
            }
            
        }
    }
}
