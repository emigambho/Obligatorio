package SessionBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import entidad.Auto;
import entidad.Jugador;
import entidad.Empleado;
import entidad.Servicio;
import entidad.Solicitud;

@Singleton
public class SistemaBean {
    
    List<Jugador> listaClientes;
    List<Empleado> listaEmpleados;
    List<Auto> listaAutos;
    List<Solicitud> listaSolicitudes;
    
    @PostConstruct
    void setup() {
        listaClientes = new ArrayList<Jugador>();
        listaEmpleados = new ArrayList<Empleado>();
        listaAutos = new ArrayList<Auto>();
        listaSolicitudes = new ArrayList<Solicitud>();
        Jugador c = new Jugador(1L, "Jose");
        Auto a = new Auto("Fiat", "Uno", 1998, "SAI4068");
        c.addToAutos(a);
        Empleado e = new Empleado(1, "Carlos");
        Solicitud s = new Solicitud(1L, a, e, new Date());
        s.addToServicios(new Servicio("LAVADO", "PENDIENTE"));
        s.addToServicios(new Servicio("BALANCEO", "PENDIENTE"));
        this.listaAutos.add(a);
        this.listaClientes.add(c);
        this.listaClientes.add(new Jugador(2L, "Maria"));
        this.listaEmpleados.add(e);
        this.listaEmpleados.add(new Empleado(2, "Daniel"));
        this.listaSolicitudes.add(s);
    }
    
    public Auto buscarAutoPorMatricula(String matricula) {
        Auto auto = null;
        for (Auto a : this.listaAutos) {
            if (a.getMatricula().equalsIgnoreCase(matricula)) {
                auto = a;
            }
        }
        return auto;
    }
    
    public Empleado buscarEmpleadoPorNumero(Integer numero) {
        Empleado empleado = null;
        for (Empleado e : this.listaEmpleados) {
            if (e.getNumero() == numero) {
                empleado = e;
            }
        }
        return empleado;
    }
    
    public Solicitud buscarSolicitudPorId(Long id) {
        Solicitud solicitud = null;
        for (Solicitud s : this.listaSolicitudes) {
            if (s.getId() == id) {
                solicitud = s;
            }
        }
        return solicitud;
    }
    
    public List<Solicitud> buscarSolicitudPorAuto(Auto auto) {
        List<Solicitud> solicitudes = new ArrayList<Solicitud>();
        for (Solicitud s : this.listaSolicitudes) {
            if (s.getAuto().equals(auto)) {
                solicitudes.add(s);
            }
        }
        return solicitudes;
    }

    public void guardarSolicitud(Solicitud solicitud) {
        this.listaSolicitudes.add(solicitud);
    }

    public Long nextIdSolicitud() {
        return this.listaSolicitudes.size() + 1L;
    }
    
    public Long nextIdCliente() {
        return this.listaClientes.size() + 1L;
    }

    public List<Jugador> listarClientes() {
        return this.listaClientes;
    }

    public Jugador guardarCliente(Jugador cliente) {
        cliente.setId(nextIdCliente());
        this.listaClientes.add(cliente);
        return cliente;
    }

    public List<Solicitud> listarSolicitidues() {
        return this.listaSolicitudes;
    }
    
}