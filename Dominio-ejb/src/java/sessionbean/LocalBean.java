package sessionbean;

import entidad.Local;
import exception.PartidoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class LocalBean {

    private Map<Long, Local> localesMap;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    private void startup() {
        List<Local> locales = buscarLocales();
        for (Local local : locales) {
            getLocalesMap().put(local.getId(), local);
        }
    }

    public Local buscarLocal(Long localId) {
        return (Local) em.createQuery("select l from Local l where l.id = :localId")
                .setParameter("localId", localId).getSingleResult();
    }

    public List<Local> buscarLocales() {
        return em.createQuery("select l from Local l").getResultList();
    }

    public Local buscarLocalMap(Long localId) throws PartidoException {
        Local local = localesMap.get(localId);
        if (local == null) {
            local = buscarLocal(localId);
        }
        if (local == null) {
            //Devolver error, local no encontrado
            throw new PartidoException();
        }
        localesMap.put(localId, local);
        return local;
    }

    public Map<Long, Local> getLocalesMap() {
        if (localesMap == null) {
            localesMap = new HashMap<>();
        }
        return localesMap;
    }

    public void setLocalesMap(Map<Long, Local> localesMap) {
        this.localesMap = localesMap;
    }
}
