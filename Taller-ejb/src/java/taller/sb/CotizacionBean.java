package taller.sb;

import javax.ejb.Singleton;

@Singleton
public class CotizacionBean {
    
    private int contador = 0;
    
    public String getContador() {
        return "CONTADOR = " + contador++;
    }

}