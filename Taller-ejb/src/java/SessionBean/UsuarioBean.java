
package SessionBean;

import entidad.Usuario;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean
public class UsuarioBean {

    public Usuario buscarUsuario(String token) {
        return null;
    }

}
