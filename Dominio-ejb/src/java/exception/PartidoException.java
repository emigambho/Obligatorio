
package exception;

public class PartidoException extends ObligatorioException{

    public PartidoException() {
    }

    public PartidoException(String codigo, String descripcion) {
        super(codigo, descripcion);
    }
   
    
    
}
