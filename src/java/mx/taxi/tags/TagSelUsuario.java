/*
 * Autor: Miguel Angel Cedeno Garciduenas
 * Email: miguecg@gmail.com 
 */

package mx.taxi.tags;

/**
 *
 * @author miguel
 */
import java.io.Serializable;
import java.io.IOException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import mx.taxi.beans.Usuario;
import mx.taxi.beans.TaxiException;

public class TagSelUsuario extends BodyTagSupport implements Serializable {

    private Long usuario;
    private String nombre;
    private boolean requerido;

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {

            Usuario usua = new Usuario();
            usua.setCondicion(" Order by usua_id ");
            out.println("<select name=\"" + nombre + "\" size=\"1\" " + (requerido ? "class=\"requerido\"" : "class=\"nrequerido\"") + ">");

            if (usuario == null) {
                out.println("<option value=\"\">-- usuario --</option>");
            }

            for (Usuario s : usua.getUsuarios()) {
                out.println("<option value=\"" + s.getUsuaUsuaid() + "\">" + s.getUsuaUsuaid() + " " + s.getUsuaUsuario() + "</option>");
            }

            out.println("</select>");

        } catch (TaxiException e) {
            throw new JspException(e);
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

}
