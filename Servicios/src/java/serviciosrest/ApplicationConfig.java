package serviciosrest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(serviciosrest.EquipoResource.class);
        resources.add(serviciosrest.PartidoResource.class);
        resources.add(serviciosrest.UsuarioResource.class);
    }
    
}