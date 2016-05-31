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
import mx.taxi.beans.Rol;
import mx.taxi.beans.Usuario;
import mx.taxi.beans.TaxiException;

public class TagRol extends BodyTagSupport {

    private Long usuario;
    private String nombre;
    private boolean requerido;

    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    public void setUsuario(Long usuario) {
        this.usuario = usuario;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {

            Rol role = new Rol();

            if (usuario != null) {
                Usuario usua = new Usuario();
                usua.setId(usuario);
                out.println("<div class=\"ui-widget-header\">Roles del usuario: " + usua.getUsuario().getUsuaNombre() + " " + usua.getUsuaApepat() + " " + usua.getUsuaApemat() + " </div>");
            }

            List<Rol> lr = role.getRoles();

            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th style=\"ui-widget-header\" style=\"width:20px\">&nbsp;</th>");
            out.println("<th style=\"ui-widget-header\">Rol</th>");
            out.println("</tr>");

            for (Rol r : lr) {

                out.println("<tr>");
                out.println("<td class=\"ui-widget-content\" style=\"width:20px\">");
                Rol ro = new Rol();
                if (usuario != null) {
                    ro.setCondicion("Where role_rol = '" + r.getRoleRol() + "' And role_usuario = '"+usuario+"'");
                    boolean check = !ro.getRoles().isEmpty();
                    out.println("<input id=\"id-" + nombre + "-" + r.getRol() + "\" name=\"" + nombre + "\" value=\""+r.getRoleRol()+"\" type=\"checkbox\" class=\""+(requerido ? "requerido" : "nrequerido")+"\" " + (!check ? "checked" : "") + "/>");
                } else {
                    out.println("<input id=\"id-" + nombre + "-" + r.getRol() + "\" name=\"" + nombre + "\" value=\""+r.getRoleRol()+"\" type=\"checkbox\" class=\""+(requerido ? "requerido" : "nrequerido")+"\"/>");
                }

                out.println("</td>");
                  out.println("<td class=\"ui-widget-content\" style=\"text-align:left\">");
                    out.println(r.getRoleDescrip());
                  out.println("</td>");                
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
