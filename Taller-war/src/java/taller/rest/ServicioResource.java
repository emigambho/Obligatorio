package taller.rest;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import taller.sb.ServicioBean;

@ManagedBean
@Path("servicios")
public class ServicioResource {
    
    @EJB
    ServicioBean servicioBean;

    @Context
    private UriInfo context;

    public ServicioResource() {
    }

    @GET
    @Produces("application/json")
    public String getJson() {
        throw new UnsupportedOperationException();
    }

    @PUT
    public Response putJson(@QueryParam("id") Long idSolicitud, @QueryParam("servicio") String servicio, @QueryParam("estado") String estado) {
        servicioBean.registrarCambioEstado(idSolicitud, servicio, estado);
        return Response.accepted().build();
    }
    
}