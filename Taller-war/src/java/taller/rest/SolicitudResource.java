package taller.rest;

import SessionBean.SolicitudBean;
import com.google.gson.Gson;
import entidad.Solicitud;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@ManagedBean
@Path("solicitudes")
public class SolicitudResource {
    
    @EJB
    private SolicitudBean solicitudBean;

    @Context
    private UriInfo context;

    private Gson gson;
    
    public SolicitudResource() {
        gson = new Gson();
    }

    @GET
    @Produces("application/json")
    public Response getJson() {
        List<Solicitud> list = solicitudBean.listarSolicitudes();
        return Response.ok(gson.toJson(list)).build();
    }
    
    @GET
    @Path("/{matricula}")
    @Produces("application/json")
    public Response getJson(@PathParam("matricula") String matricula) {
        List<Solicitud> s = solicitudBean.consultarSolicitudes(matricula);
        return Response.ok(gson.toJson(s)).build();
    }

}