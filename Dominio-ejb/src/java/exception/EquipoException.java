
package exception;

public class EquipoException extends ObligatorioException{

    public EquipoException() {
    }

    public EquipoException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }
    
}
