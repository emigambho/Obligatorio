package SessionBean;

import entidad.Equipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entidad.Jugador;
import entidad.Partido;

@Stateless
public class EquipoBean {

    @PersistenceContext
    EntityManager em;

    public Equipo CrearEquipo(List<Jugador> jugadores, Integer clasificacion, String color, List<Partido> partidos, String nombre) {
        Equipo equipo = new Equipo(jugadores, clasificacion, color, partidos, nombre);
        em.persist(equipo);
        return equipo;
    }

    public Equipo BuscarEquipo(Long id) {
        return (Equipo) em.createQuery("select e from Equipo e where e.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    public Jugador BuscarJugador(Long id) {
        return (Jugador) em.createQuery("select j from Jugador j where j.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    public Boolean IncribirJugadorEquipo(Long idEquipo, Long idJugador) {
        Jugador jugador = BuscarJugador(idJugador);
        Equipo equipo = BuscarEquipo(idEquipo);
        if (equipo.getJugadores().size() < 5 || !equipo.getJugadores().contains(jugador)) {
            equipo.getJugadores().add(jugador);
            return true;
        }
        return false;
        //faltaría guardarlo en la base
    }
    public boolean BorrarJugadorEquipo(Long idEquipo, Long idJugador){
        Jugador jugador = BuscarJugador(idJugador);
        Equipo equipo = BuscarEquipo(idEquipo);
        if (equipo.getJugadores().contains(jugador)) {
            equipo.getJugadores().remove(jugador);
            return true;
        }
        return false;
        //faltaría guardarlo en la base
    }

}
