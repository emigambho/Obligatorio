package taller.sb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import taller.entidad.Cliente;

@Stateless
public class ClienteBean {
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    private SistemaBean sistemaBean;
    
    public Cliente crearCliente(Cliente cliente) {
        em.persist(cliente);
        return cliente;
    }
    
    public List<Cliente> listarClientes() {
        return em.createQuery("select c from Cliente c").getResultList();
    }
    
    public List<Cliente> buscarClientePorNombre(String nombre) {
        return em.createQuery("select c from Cliente c where nombre = :n")
                .setParameter("n", nombre)
                .getResultList();
    }

}