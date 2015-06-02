package SessionBean;

import entidad.Solicitud;
import entidad.Empleado;
import entidad.Auto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class SolicitudBean {
    
    @EJB
    private SistemaBean sistemaBean;
    
    public Solicitud crearSolicitud(String matricula, Integer numEmpleado) {
        Solicitud solicitud = null;
        Auto auto = sistemaBean.buscarAutoPorMatricula(matricula);
        Empleado empleado = sistemaBean.buscarEmpleadoPorNumero(numEmpleado);
        if (auto != null && empleado != null) {
            Long id = sistemaBean.nextIdSolicitud();
            solicitud = new Solicitud(id, auto, empleado, new Date());
            sistemaBean.guardarSolicitud(solicitud);
        }
        return solicitud;
    }
    
    public List<Solicitud> consultarSolicitudes(String matricula) {
        List<Solicitud> resultados = new ArrayList<Solicitud>();
        Auto auto = sistemaBean.buscarAutoPorMatricula(matricula);
        if (auto != null) {
            resultados = sistemaBean.buscarSolicitudPorAuto(auto);
        }
        return resultados;
    }

    public List<Solicitud> listarSolicitudes() {
        return sistemaBean.listarSolicitidues();
    }
    
}