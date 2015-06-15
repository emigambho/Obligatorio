package serviciosRest;

import SessionBean.PartidoBean;
import SessionBean.UsuarioBean;
import com.google.gson.Gson;
import entidad.Jugador;
import entidad.Partido;
import entidad.Usuario;
import java.util.Date;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@ManagedBean
@Path("partidos")
public class PartidoResource {
    
    private static final String VACIO = "";
    
    private Gson gson;
    
    @EJB
    PartidoBean partidoBean;
    
    @EJB
    UsuarioBean usuarioBean;

    public PartidoResource() {
        gson = new Gson();
    }

    @GET
    @Produces("application/json")
    public Response getJson() {
        List<Partido> list = partidoBean.listarPartidos();        
        return Response.ok(gson.toJson(list)).build();
    }
    
    /**
     * Un Jugador se registra a una cola de espera para armar el armado de un 
     * partido en una determinada hora.
     * Validaciones:
     *  -La hora debe estar entre 0 y 23
     *  -El token no puede ser vacio
     *  -El token debe corresponder a un jugador
     * @param fecha 
     * @param token 
     * @return 
     */
    @PUT
    @Path("registrarJugadorAPartido")
    public Response registrarJugadorAPartido(@QueryParam("fecha") Date fecha, @HeaderParam("token") String token) {
        if(VACIO.equals(token) || fecha != null){
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            Usuario user = usuarioBean.buscarUsuario(token);
            if(user != null){
                if(user instanceof Jugador){
                    Jugador jugador = (Jugador) user;
                    partidoBean.registrarJugadorAPartido(fecha,jugador);
                    return Response.accepted().build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
               return Response.status(Status.UNAUTHORIZED).build(); 
            }
        }
    }
    
     /**
     * Un Jugador se registra a una cola de espera para armar el armado de un 
     * partido en una determinada hora.
     * Validaciones:
     *  -La hora debe estar entre 0 y 23
     *  -El token no puede ser vacio
     *  -El token debe corresponder a un jugador
     * @param fecha 
     * @param id
     * @param token 
     * @return 
     */
    @PUT
    @Path("registrarEquipoAPartido")
    public Response registrarEquipoAPartido(@QueryParam("fecha") Date fecha, @QueryParam("id") Long id, @QueryParam("token") String token) {
        if(VACIO.equals(token)){
            return Response.status(Status.BAD_REQUEST).build();
        } else {
            Usuario user = usuarioBean.buscarUsuario(token);
            if(user != null){
                if(user instanceof Jugador){
                    Jugador jugador = (Jugador) user;
                    partidoBean.registrarEquipoAPartido(fecha,null);
                    return Response.accepted().build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }
            } else {
               return Response.status(Status.UNAUTHORIZED).build(); 
            }
        }
    }
    
//
//    @PUT
//    public Response putJson(@QueryParam("id") Long idSolicitud, @QueryParam("servicio") String servicio, @QueryParam("estado") String estado) {
//        servicioBean.registrarCambioEstado(idSolicitud, servicio, estado);
//        return Response.accepted().build();
//    }
    
}