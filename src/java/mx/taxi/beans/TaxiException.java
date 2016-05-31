/*
 * Autor: Miguel Angel Cedeno Garciduenas
 * Email: miguecg@gmail.com 
 */

package mx.taxi.beans;

/**
 *
 * @author miguel
 */
public class TaxiException extends Exception {

    public TaxiException() {
    }

    public TaxiException(String message) {
        super(message);
    }

    public TaxiException(Throwable cause) {
        super(cause);
    }

    public TaxiException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
