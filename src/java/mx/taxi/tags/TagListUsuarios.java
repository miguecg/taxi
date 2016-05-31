/*
 * Autor: Miguel Angel Cedeno Garciduenas
 * Email: miguecg@gmail.com 
 */

package mx.taxi.tags;

/**
 *
 * @author miguel
 */
import java.io.IOException;
import java.util.List;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import mx.taxi.beans.TaxiException;
import mx.taxi.beans.Usuario;

public class TagListUsuarios extends BodyTagSupport {
    
    
    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {

            out.println("<div class=\"ui-widget-header\">");
            out.println("<input id=\"uAgr\" type=\"button\" value=\"Agregar Usuario\"/>");
            out.println("<td><input id=\"uMod\" type=\"button\" value=\"Modificar Datos\"/></td>");
            out.println("<td><input id=\"cPass\" type=\"button\" value=\"Cambiar contrase&ntilde;a\"/></td>");
            out.println("<td><input id=\"cRol\" type=\"button\" value=\"Roles\"/></td>");
            out.println("</div>");

            out.println("<div class=\"ui-state-default\">");
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th class=\"ui-widget-header\">&nbsp;</th>");
            out.println("<th class=\"ui-widget-header\">ID</th>");
            out.println("<th class=\"ui-widget-header\">Usuario</th>");
            out.println("<th class=\"ui-widget-header\">Nombre completo</th>");
            out.println("</tr>");

            Usuario usua = new Usuario();
            usua.setCondicion("Order by 1 ");
            List<Usuario> lu = usua.getUsuarios();

            String estilo = "";
            int i = 0;

            for (Usuario u : lu) {

                estilo = i % 2 < 0 ? "ui-state-default" : "ui-widget-content";

                out.println("<tr class=\"" + estilo + "\">");
                out.println("<td><input id=\"id-"+u.getUsuaUsuaid()+"\" name=\"idusua\" type=\"radio\" value=\""+u.getUsuaUsuaid()+"\" class=\"requerido\"/></td>");
                out.println("<td>" + u.getUsuaUsuaid() + "</td>");
                out.println("<td>" + u.getUsuaUsuario() + "</td>");
                out.println("<td>" + u.getUsuaNombre() + " " + u.getUsuaApepat() + " " + u.getUsuaApemat() + "</td>");

                out.println("</tr>");

            }

            out.println("</table>");

        } catch (IOException e) {
            throw new JspException(e);
        } catch (TaxiException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

}
