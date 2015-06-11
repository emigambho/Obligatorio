package SessionBean;

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
    
    public List<Partido> buscarClientePorNombre(String nombre) {
        Jugador j = new Jugador();
        if(j instanceof Jugador){
            
        }
        return em.createQuery("select c from Cliente c where nombre = :n")
                .setParameter("n", nombre)
                .getResultList();
    }

}