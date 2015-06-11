package SessionBean;

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
    
    public Partido crearCliente(Partido cliente) {
        em.persist(cliente);
        return cliente;
    }
    
    public List<Partido> listarClientes() {
        return em.createQuery("select c from Cliente c").getResultList();
    }
    
    public List<Partido> buscarClientePorNombre(String nombre) {
        return em.createQuery("select c from Cliente c where nombre = :n")
                .setParameter("n", nombre)
                .getResultList();
    }

}