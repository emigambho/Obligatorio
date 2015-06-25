package serviciosrest;

import com.google.gson.Gson;

import entidad.Jugador;
import exception.EquipoException;
import usuario.util.UsuarioOAuth;
import sessionbean.EquipoBean;
import sessionbean.UsuarioBean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;



@ManagedBean
@Path("equipos")
public class EquipoResource {

    private static final String VACIO = "";

    private final Gson gson;

    @EJB
    EquipoBean equipoBean;
    
    @EJB
    UsuarioBean usuarioBean;

    public EquipoResource() {
        gson = new Gson();
    }
    
    @POST
    @Path("crearEquipo")
    public Response crearEquipo(@QueryParam("nombre") String nombre, @QueryParam("color") String color, @HeaderParam("token") String token) {
        if (VACIO.equals(token) || VACIO.equals(nombre) || VACIO.equals(color)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esJugador()) {
                    Jugador jugador = user.getJugador();
                    List<Jugador> jugadores = new ArrayList<>();
                    jugadores.add(jugador);
                    equipoBean.crearEquipo(jugadores, 0, color, null, nombre);
                    return Response.ok().build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        }
    }

    
    @PUT
    @Path("incribirJugadorEquipo")
    public Response incribirJugadorEquipo(@QueryParam("idEquipo") Long idEquipo, @QueryParam("idJugador") Long idJugador, @HeaderParam("token") String token) {
        if (VACIO.equals(token) || idEquipo == null || idJugador == null) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esJugador()) {
                    try {
                        equipoBean.incribirJugadorEquipo(idEquipo, idJugador);
                    } catch (EquipoException ex) {
                        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getDescripcion()).build();
                    }
                    return Response.accepted().build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        }
    }
    
    @PUT
    @Path("borrarJugadorEquipo")
    public Response borrarJugadorEquipo(@QueryParam("idEquipo") Long idEquipo, @QueryParam("idJugador") Long idJugador, @HeaderParam("token") String token) {
        if (VACIO.equals(token) || idEquipo == null || idJugador == null) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esJugador()) {
                    try {
                        equipoBean.borrarJugadorEquipo(idEquipo, idJugador);
                    } catch (EquipoException ex) {
                        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getDescripcion()).build();
                    }
                    return Response.accepted().build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        }
    }  
}
