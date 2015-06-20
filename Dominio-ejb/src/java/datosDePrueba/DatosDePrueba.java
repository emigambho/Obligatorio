package datosDePrueba;

import entidad.Administrador;
import entidad.Cancha;
import entidad.Equipo;
import entidad.Jugador;
import entidad.Local;
import entidad.Partido;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import partido.util.EstadoPartido;

public class DatosDePrueba {

    private void cargarDatos() throws ParseException {
        
    }
    
    public static  void main(String[] args) throws ParseException {
        new DatosDePrueba().cargarDatos();
    }

}
