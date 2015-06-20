package serviciosRest;

import SessionBean.PartidoBean;
import SessionBean.UsuarioBean;
import com.google.gson.Gson;
import entidad.Administrador;
import entidad.Jugador;
import entidad.Partido;
import entidad.Usuario;
import exception.PartidoException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import partido.util.EstadoPartido;
import usuario.util.UsuarioOAuth;

@ManagedBean
@Path("usuarios")
public class UsuarioResource {

    private static final String VACIO = "";

    private Gson gson;
   
    //iniciarSesionJugador
    //iniciarSesionAdministrador
    //listrUsu

 
    
    
    @EJB
    UsuarioBean usuarioBean;

    public UsuarioResource() {
        gson = new Gson();
    }
    
    @GET
    @Produces("application/json")
    public Response listarUsuario() {
        List<Usuario> list = usuarioBean.ListarUsuario();
        return Response.ok(gson.toJson(list)).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("jugador/")
    public Response listarJuadores() {
        List<Jugador> list = usuarioBean.ListarJugadores();
        return Response.ok(gson.toJson(list)).build();
    }
    
    @GET
    @Produces("application/json")
    @Path("administrador/")
    public Response listarAdministrador() {
        List<Administrador> list = usuarioBean.ListarAdministradores();
        return Response.ok(gson.toJson(list)).build();
    }
    @GET
    @Produces("application/json")
    @Path("jugador/{id}")
    public Response buscarJugador(@PathParam("id") Long idPartido) {
        Jugador jugador = usuarioBean.buscarJugador(idPartido);
        return Response.ok(gson.toJson(jugador)).build();
    }
    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response buscarAdministrador(@PathParam("id") Long idPartido) {
        Administrador administradr = usuarioBean.buscarAdminstradorId(idPartido);
        return Response.ok(gson.toJson(administradr)).build();
    }   
//    @PUT
//    @Path("registrarJugadorAPartido")
//    public Response registrarJugadorAPartido(@QueryParam("fecha") Date fecha, @HeaderParam("token") String token) {
//        if (VACIO.equals(token) || fecha != null) {
//            return Response.status(Status.BAD_REQUEST).build();
//        } else {
//            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
//            if (user != null) {
//                if (user.esJugador()) {
//                    Jugador jugador = user.getJugador();
//                    partidoBean.registrarJugadorAPartido(fecha, jugador);
//                    return Response.accepted().build();
//                } else {
//                    return Response.status(Status.FORBIDDEN).build();
//                }
//            } else {
//                return Response.status(Status.UNAUTHORIZED).build();
//            }
//        }
//    }

    
//    @PUT
//    @Path("crearPartido")
//    public Response crearPartido(@QueryParam("idEquipoA") Long idEquipoA, @QueryParam("idEquipoB") Long idEquipoB, @QueryParam("fechaInicio") Date fechaInicio, @QueryParam("fechaFin") Date fechaFin, @HeaderParam("token") String token) {
//        if (VACIO.equals(token)) {
//            return Response.status(Status.BAD_REQUEST).build();
//        } else {
//            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
//            if (user != null) {
//                if (user.esAdministrador()) {
//                    Administrador administrador = user.getAdministrador();
//                    partidoBean.crearPartido(idEquipoA, idEquipoB, fechaInicio, fechaFin, EstadoPartido.RESERVADO, administrador);
//                    return Response.accepted().build();
//                } else {
//                    return Response.status(Status.FORBIDDEN).build();
//                }
//            } else {
//                return Response.status(Status.UNAUTHORIZED).build();
//            }
//        }
//    }

}