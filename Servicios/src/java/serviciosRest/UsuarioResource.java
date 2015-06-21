package serviciosRest;

import SessionBean.UsuarioBean;
import com.google.gson.Gson;
import entidad.Administrador;
import entidad.Jugador;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import usuario.util.UsuarioOAuth;

@ManagedBean
@Path("usuarios")
public class UsuarioResource {

    private static final String VACIO = "";

    private Gson gson;
    
    @EJB
    UsuarioBean usuarioBean;

    public UsuarioResource() {
        gson = new Gson();
    }
    
    @PUT
    @Produces("application/json")
    @Path("IniciarSesionJugador")
    public Response IniciarSesionJugador(@QueryParam("email") String email,@QueryParam("contraseña") String contraseña) {
        UsuarioOAuth user;
        user = usuarioBean.IniciarSesionJugador(email,contraseña);
        return Response.ok(user).build();
    }
    
    @PUT
    @Produces("application/json")
    @Path("IniciarSesionAdministrador")
    public Response IniciarSesionAdministrador(@QueryParam("email") String email,@QueryParam("contraseña") String contraseña) {
        UsuarioOAuth user;
        user = usuarioBean.IniciarSesionAdministrador(email,contraseña);
        return Response.ok(user).build();
    }
    
}
