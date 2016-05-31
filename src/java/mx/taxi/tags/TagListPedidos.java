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
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.http.HttpServletRequest;
import mx.taxi.beans.Pedido;
import mx.taxi.beans.Usuario;
import mx.taxi.beans.Rol;
import mx.taxi.beans.Taxi;
import mx.taxi.beans.TaxiException;

public class TagListPedidos extends BodyTagSupport {

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        try {

            Usuario usua = (Usuario) request.getSession().getAttribute("USER_AUTH");
            List<Rol> lr = (List<Rol>) request.getSession().getAttribute("USER_ROL");
            Pedido pe = new Pedido();
            Rol rol = new Rol();

            if (rol.isRol(1l, lr) || rol.isRol(2l, lr) || rol.isRol(3l, lr)) {
                pe.setCondicion(" Order by 1 desc ");
            } else {
                pe.setCondicion(" Where pedi_usuario = '" + usua.getUsuaUsuaid() + "' Order by pedi_pedido desc ");
            }
            out.println("<div class=\"ui-widget-header\">Pedidos de taxis</div>");
            out.println("<table style=\"width:100%\">");
            out.println("<tr>");
            out.println("<th class=\"ui-widget-header\">ID</th>");
            out.println("<th class=\"ui-widget-header\">Estatus</th>");
            out.println("<th class=\"ui-widget-header\">Fecha</th>");
            out.println("<th class=\"ui-widget-header\">Latitud</th>");
            out.println("<th class=\"ui-widget-header\">Longitud</th>");
            out.println("<th class=\"ui-widget-header\">Monto</th>");
            out.println("<th class=\"ui-widget-header\">Taxi</th>");
            out.println("</tr>");

            List<Pedido> lp = pe.getPedidos();

            for (Pedido p : lp) {
                out.println("<tr>");
                out.println("<td class=\"ui-widget-content\">" + p.getPediPedido() + "</td>");
                out.println("<td class=\"ui-widget-content\">");

                if (p.getPediEstatus().equals("P")) {
                    out.println("Pendiente");
                } else if (p.getPediEstatus().equals("A")) {
                    out.println("Atendido");
                } else if (p.getPediEstatus().equals("S")) {
                    out.println("Seleccionado");
                } else if (p.getPediEstatus().equals("C")) {
                    out.println("Cancelado");
                }

                out.println("</td>");

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM d HH:mm:ss yyyy");

                out.println("<td class=\"ui-widget-content\">" + sdf.format(p.getPediFecha()) + "</td>");
                out.println("<td class=\"ui-widget-content\">" + p.getPediLatitud() + "</td>");
                out.println("<td class=\"ui-widget-content\">" + p.getPediLongitud() + "</td>");
                out.println("<td class=\"ui-widget-content\">" + (p.getPediMonto() > 0.0 ? p.getPediMonto() : "-") + "</td>");
                out.println("<td class=\"ui-widget-content\">");

                Taxi t = new Taxi();
                if (p.getPediTaxi() != null || p.getPediTaxi() == 0.0) {
                    t.setId(p.getPediTaxi());
                    t = t.getTaxi();
                    out.println(t.getTaxiTaxi() + " " + t.getTaxiPlaca());
                } else {
                    out.println("-");
                }

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
