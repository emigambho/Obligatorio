package SessionBean;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entidad.Jugador;

@Stateless
public class EquipoBean {
    
    @PersistenceContext
    EntityManager em;
    
    public Jugador crearCliente(Jugador cliente) {
        em.persist(cliente);
        return cliente;
    }
    
    public List<Jugador> listarClientes() {
        return em.createQuery("select c from Cliente c").getResultList();
    }
    
    public List<Jugador> buscarClientePorNombre(String nombre) {
        return em.createQuery("select c from Cliente c where nombre = :n")
                .setParameter("n", nombre)
                .getResultList();
    }

}