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
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import mx.taxi.beans.Pedido;
import mx.taxi.beans.Usuario;
import mx.taxi.beans.TaxiException;

public class TagSelPedido extends BodyTagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        Usuario usua = (Usuario) request.getSession().getAttribute("USER_AUTH");
        Double lat = request.getParameter("v_lat") != null ? Double.valueOf(request.getParameter("v_lat")) : null;
        Double lon = request.getParameter("v_lon") != null ? Double.valueOf(request.getParameter("v_lon")) : null;

        try {

            out.println("");
            Pedido ped = new Pedido();
            ped.setCondicion(" Where pedi_estatus = 'P' Order by pedi_fecha ");
            List<Pedido> lp = ped.getPedidos();

            out.println("<div class=\"ui-widget-content\">");            
            out.println("<input id=\"vhub\" type=\"button\" value=\"Ver hubicaci&oacute;n del pedido\"/>");
            out.println("<input id=\"sel\" type=\"button\" value=\"Seleccionar\"/>");
            out.println("</div>");

            out.println("<table style=\"width:100%\">");

            out.println("<tr>");
            out.println("<th class=\"ui-widget-header\">&nbsp;</th>");
            out.println("<th class=\"ui-widget-header\">Usuario</th>");
            out.println("<th class=\"ui-widget-header\">Fecha</th>");
            out.println("<th class=\"ui-widget-header\">Distancia</th>");
            out.println("</tr>");

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            for (Pedido p : lp) {

                out.println("<tr>");

                out.println("<td class=\"ui-widget-content\">");
                out.println("<inpu type=\"radio\" value=\"" + p.getPediPedido() + "\" name=\"v_pedido\"/>");
                out.println("</td>");

                Usuario usu = new Usuario();
                usu.setId(p.getPediUsuario());
                usu = usu.getUsuario();

                out.println("<td class=\"ui-widget-content\">" + usu.getUsuaNombre() + " " + usu.getUsuaApepat() + " " + (usu.getUsuaApemat() != null ? usu.getUsuaApemat() : "") + "</td>");

                out.println("<td class=\"ui-widget-content\">" + sdf.format(p.getPediFecha()) + "</td>");

                Double dist = p.distanciaPuntos(lat, p.getPediLatitud(), lon, p.getPediLongitud());

                out.println("<td class=\"ui-widget-content\">" + dist + " KM</td>");

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
