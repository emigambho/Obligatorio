package taller.rest;

import SessionBean.ClienteBean;
import com.google.gson.Gson;
import entidad.Cliente;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@ManagedBean
@Path("clientes")
public class ClienteResource {
    
    @EJB
    private ClienteBean clienteBean;

    @Context
    private UriInfo context;
    
    private Gson gson;

    public ClienteResource() {
        gson = new Gson();
    }

    @GET
    @Produces("application/json")
    public Response getJson() {
        List<Cliente> list = clienteBean.listarClientes();
        return Response.ok(gson.toJson(list)).build();
    }
    
    @POST
    @Consumes("application/json")
    public Response postJson(String content) {
        Cliente cliente = gson.fromJson(content, Cliente.class);
        Cliente resp = clienteBean.crearCliente(cliente);
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }
    
}