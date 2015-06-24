package serviciosrest;

import com.google.gson.Gson;

import sessionbean.UsuarioBean;
import usuario.util.UsuarioOAuth;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

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
    public Response IniciarSesionJugador(@QueryParam("email") String email, @QueryParam("contraseña") String contraseña) {
        UsuarioOAuth user;
        user = usuarioBean.iniciarSesionJugador(email, contraseña);
        return Response.ok(user).build();
    }

    @PUT
    @Produces("application/json")
    @Path("IniciarSesionAdministrador")
    public Response IniciarSesionAdministrador(@QueryParam("email") String email, @QueryParam("contraseña") String contraseña) {
        UsuarioOAuth user;
        user = usuarioBean.iniciarSesionAdministrador(email, contraseña);
        return Response.ok(user).build();
    }

}
