package serviciosRest;

import SessionBean.PartidoBean;
import SessionBean.UsuarioBean;
import com.google.gson.Gson;
import entidad.Administrador;
import entidad.Jugador;
import entidad.Partido;
import exception.EquipoException;
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
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import partido.util.EstadoPartido;
import usuario.util.UsuarioOAuth;

@ManagedBean
@Path("partidos")
public class PartidoResource {

    private static final String VACIO = "";

    private final Gson gson;

    @EJB
    PartidoBean partidoBean;

    @EJB
    UsuarioBean usuarioBean;

    public PartidoResource() {
        gson = new Gson();
    }

    @GET
    @Produces("application/json")
    public Response listarPartidos() {
        List<Partido> list = partidoBean.listarPartidos();
        return Response.ok(gson.toJson(list)).build();
    }

    @GET
    @Produces("application/json")
    @Path("estado/{id}")
    public Response verEstadoPartido(@PathParam("id") Long idPartido) {
        EstadoPartido estado = partidoBean.verEstadoPartido(idPartido);
        return Response.ok(gson.toJson(estado)).build();
    }
    @GET
    @Produces("application/json")
    @Path("{id}")
    public Response verInfoPartido(@PathParam("id") Long idPartido) {
        Partido partido = partidoBean.verInfoPartido(idPartido);
        return Response.ok(gson.toJson(partido)).build();
    }

    @PUT
    @Path("registrarJugadorAPartido")
    public Response registrarJugadorAPartido(@QueryParam("fecha") String fecha, @QueryParam("local") Long localId, @HeaderParam("token") String token) {
        if (VACIO.equals(token) || fecha == null) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esJugador()) {
                    Jugador jugador = user.getJugador();
                    partidoBean.registrarJugadorAPartido(fecha, jugador, localId);
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
    @Path("registrarEquipoAPartido")
    public Response registrarEquipoAPartido(@QueryParam("fecha") String fecha, @QueryParam("equipo") Long equipoId, @QueryParam("local") Long localId, @HeaderParam("token") String token) {
        if (VACIO.equals(token)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esJugador()) {
                    try {
                        partidoBean.registrarEquipoAPartido(fecha, equipoId, localId, user.getJugador());
                    } catch (EquipoException ex) {
                        Response.status(Status.PRECONDITION_FAILED).entity(ex.getDescripcion()).build();
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

    @POST
    @Path("crearPartido")
    public Response crearPartido(@QueryParam("idEquipoA") Long idEquipoA, @QueryParam("idEquipoB") Long idEquipoB, @QueryParam("fechaInicio") Date fechaInicio, @QueryParam("fechaFin") Date fechaFin, @HeaderParam("token") String token) {
        if (VACIO.equals(token)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esAdministrador()) {
                    Administrador administrador = user.getAdministrador();
                    partidoBean.crearPartido(idEquipoA, idEquipoB, fechaInicio, fechaFin, EstadoPartido.RESERVADO, administrador);
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
    @Path("confirmarAPartido")
    public Response confirmarAPartido(@QueryParam("id") Long id, @QueryParam("fechaInicio") Date fechaInicio, @HeaderParam("token") String token) {
        if (VACIO.equals(token)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esAdministrador()) {
                    try {
                        Administrador administrador = user.getAdministrador();
                        partidoBean.confirmarPartido(id, fechaInicio, administrador);
                        return Response.accepted().build();
                    } catch (PartidoException ex) {
                        Logger.getLogger(PartidoResource.class.getName()).log(Level.SEVERE, null, ex);
                        return Response.serverError().entity(ex).build();
                    }
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        }
    }

    @PUT
    @Path("cancelarPartido")
    public Response cancelarPartido(@QueryParam("id") Long id, @HeaderParam("token") String token) {
        if (VACIO.equals(token)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esAdministrador()) {
                    Administrador administrador = user.getAdministrador();
                    partidoBean.cancelarPartido(id, administrador);
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
    @Path("terminarPartido")
    public Response terminarPartido(@QueryParam("id") Long id, @QueryParam("fechaInicio") Date fechaIinicio, @QueryParam("golesB") Integer golesB, @QueryParam("golesA") Integer golesA, @HeaderParam("token") String token) {
        if (VACIO.equals(token)) {
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            UsuarioOAuth user = usuarioBean.buscarUsuario(token);
            if (user != null) {
                if (user.esAdministrador()) {
                    try {
                        Administrador administrador = user.getAdministrador();
                        partidoBean.terminarPartido(id, fechaIinicio, golesA, golesB, administrador);
                        return Response.accepted().build();
                    } catch (PartidoException ex) {
                        Logger.getLogger(PartidoResource.class.getName()).log(Level.SEVERE, null, ex);
                        return Response.serverError().entity(ex).build();
                    }
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
                return Response.status(Status.UNAUTHORIZED).build();
            }
        }
    }
}
