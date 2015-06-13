package SessionBean;

import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PartidoBean {

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
        return (Partido) em.createQuery("select p from Partido p where idPartido = :idPartido")
                .setParameter("idPartido", idPartido).getSingleResult();
    }

    public List<Equipo> ObtenerEquiposPartido(Long idPartido) {
        Partido partido = BuscarPartido(idPartido);
        List<Equipo> equipos = null;
        equipos.add(partido.getEquipoB());
        equipos.add(partido.getEquipoA());
        return equipos;
    }

    public void PuntuarEquipo(Long idPartido) {
        List<Equipo> equipos = ObtenerEquiposPartido(idPartido);
        Equipo equipoA = equipos.get(1);
        Equipo equipoB = equipos.get(2);
        Integer clasificacionEquipoA = equipoA.getClasificacion();
        Integer clasificacionEquipoB = equipoB.getClasificacion();
        Partido partido = BuscarPartido(idPartido);
        Integer golesEquipoA = partido.getGolesA();
        Integer golesEquipoB = partido.getGolesB();
       //SI EMPATAN 1 PUNTO PARA CADA UNO, AL MENOS QUE YA TENGAN 10 PUNTOS LOS EQUIPOS
        if (golesEquipoA == golesEquipoB) {
            if (clasificacionEquipoA < 10) {
                clasificacionEquipoA++;
            }
            if (clasificacionEquipoB < 10) {
                clasificacionEquipoB++;
            }
        // SI GANA EL PARTIDO "A", SON DOS PUNTOS PARA "A" Y -1 PARA "B", AL MENOS QUE "A" TENGA 10(NO SUMA) Y SI TIENE 9 PUNTOS 
        //SUMA SOLO 1 Y "B" SI TIENE PUNTAJE 0 NO RESTA, SI EL PARTIDO O GANA "B" IDEM CON LOS PUNTAJES CAMBIADOS
        } else if (golesEquipoA > golesEquipoB) {
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
        //FALTARÍA GUARDAR LOS RESULTADOS EN LA BASE
    }

    public List<Partido> buscarClientePorNombre(String nombre) {
        Jugador j = new Jugador();
        if (j instanceof Jugador) {

        }
        return em.createQuery("select c from Cliente c where nombre = :n")
                .setParameter("n", nombre)
                .getResultList();
    }

}
