package sessionbean;

import entidad.Equipo;
import entidad.Jugador;
import entidad.Partido;
import exception.EquipoException;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class EquipoBean {

    @PersistenceContext
    EntityManager em;

    @EJB
    UsuarioBean usuarioBean;

    public Equipo crearEquipo(List<Jugador> jugadores, Integer clasificacion, 
            String color, List<Partido> partidos, String nombre) {
        Equipo equipo = new Equipo(jugadores, clasificacion, color, partidos, nombre);
        em.persist(equipo);
        return equipo;
    }

    public Equipo buscarEquipo(Long id) {
        return (Equipo) em.createQuery("select e from Equipo e where e.id = :id")
                .setParameter("id", id).getSingleResult();
    }

    public Boolean incribirJugadorEquipo(Long idEquipo, Long idJugador) throws EquipoException {
        Jugador jugador = usuarioBean.buscarJugador(idJugador);
        Equipo equipo = buscarEquipo(idEquipo);
        if (jugador == null) {
            throw new EquipoException("Equipo.error.1.1", "jugador no existe");
        }
        if (equipo == null) {
            throw new EquipoException("Equipo.error.1.2", "equipo no existe");
        }
        if (equipo.getJugadores().size() < 5) {
            if (!equipo.getJugadores().contains(jugador)) {
                equipo.getJugadores().add(jugador);
                em.persist(equipo);
                return true;
            } else {
                throw new EquipoException("Equipo.error.2.1", "el quipo ya contiene a ese jugador");
            }

        } else {
            throw new EquipoException("Equipo.error.2.2", "Equipo ya está completo");

        }

    }

    public boolean borrarJugadorEquipo(Long idEquipo, Long idJugador) throws EquipoException {
        Jugador jugador = usuarioBean.buscarJugador(idJugador);
        Equipo equipo = buscarEquipo(idEquipo);
        if (jugador != null) {
            if (equipo != null) {
                if (equipo.getJugadores().contains(jugador)) {
                    equipo.getJugadores().remove(jugador);
                    return true;
                } else {
                    throw new EquipoException("Equipo.error.2.3", "el quipo no contiene a ese jugador");
                }
            }
            throw new EquipoException("Equipo.error.1.2", "equipo no existe");
        }
        throw new EquipoException("Equipo.error.1.1", "jugador no existe");

    }

}
