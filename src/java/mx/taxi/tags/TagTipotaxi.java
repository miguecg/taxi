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
import mx.taxi.beans.Tipotaxi;

public class TagTipotaxi extends BodyTagSupport {

    private String nombre;
    private boolean requerido;
    private Long select;
    private boolean admin;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSelect(Long select) {
        this.select = select;
    }
    
    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {
            
            if (admin) {
                out.println("<div class=\"ui-widget-content\">");
                    out.println("<input id=\"aTT\" type=\"button\" value=\"Agregar Tipo Taxi\"/>");
                    out.println("<input id=\"mTT\" type=\"button\" value=\"Actualizar Tipo Taxi\"/>");
                out.println("</div>");
            }

            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th class=\"ui-widget-header\" style=\"width:20px\">&nbsp;</th>");
            out.println("<th class=\"ui-widget-header\">Tipo de Taxi</th>");
            out.println("</tr>");

            Tipotaxi tt = new Tipotaxi();
            tt.setCondicion("Order by 1");

            List<Tipotaxi> lt = tt.getTiposTaxi();

            for (Tipotaxi t : lt) {
                out.println("<tr>");
                out.println("<td class=\"ui-widget-content\">");
                
                if (select != null && t.getTiptTipotaxi().equals(select)) {
                    out.println("<input id=\"id-"+nombre+"-"+t.getTiptTipotaxi()+"\" value=\""+t.getTiptTipotaxi()+"\" type=\"radio\" name=\""+nombre+"\" class=\""+(!requerido ? "nrequerido" : "requerido")+"\" checked/>");
                } else {
                    out.println("<input id=\"id-"+nombre+"-"+t.getTiptTipotaxi()+"\" type=\"radio\" value=\""+t.getTiptTipotaxi()+"\" name=\""+nombre+"\" class=\""+(!requerido ? "nrequerido" : "requerido")+"\"/>");
                }
                
                out.println("</td>");
                
                out.println("<td class=\"ui-widget-content\">");
                
                  out.println("<ul>");
                    out.println("<li>Tarifa: $"+t.getTiptPrecio()+"</li>");
                    out.println("<li>Descripci&oacute;n: "+t.getTiptDescrip()+"</li>");
                    out.println("<li>Texto: "+t.getTiptTexto()+"</li>");
                  out.println("</ul>");
                
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
