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
import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import mx.taxi.beans.Taxi;
import mx.taxi.beans.Tipotaxi;
import mx.taxi.beans.Usuario;
import mx.taxi.beans.TaxiException;
import mx.mig.dbc.Dao;

public class TagTaxi extends BodyTagSupport {

    private String nombre;
    private boolean requerido;
    private boolean admin;

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setRequerido(boolean requerido) {
        this.requerido = requerido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int doStartTag() throws JspException {

        JspWriter out = pageContext.getOut();

        try {

            Dao dao = new Dao();

            Taxi tx = new Taxi(dao);
            tx.setCondicion("Where taxi_estatus = 'A' And taxi_usuario != 0 Order by 1");
            List<Taxi> lt = tx.getTaxis();

            if (admin) {
                out.println("<div class=\"ui-widget-header\">");
                out.println("<input id=\"tAgr\" type=\"button\" value=\"Agregar taxi\">");
                out.println("<input id=\"tAct\" type=\"button\" value=\"Modificar taxi\">");
                out.println("<input id=\"tAsigC\" type=\"button\" value=\"De/Asignar conductor\">");
                out.println("</div>");
            } else {
                out.println("<div class=\"ui-widget-header\">Puede elegir el taxi de su preferencia de manera opcional.</div>");
            }

            out.println("<table style=\"width:100%\">");

            for (Taxi t : lt) {
                Tipotaxi tt = new Tipotaxi(dao);
                tt.setId(t.getTaxiTipotaxi());
                tt = tt.getTipoTaxi();
                Usuario usua = new Usuario(dao);                
                usua.setId(t.getTaxiUsuario());
                usua = usua.getUsuario();
                out.println("<tr>");
                out.println("<td class=\"ui-widget-content\" style=\"width:20px\">");
                out.println("<input id=\"id-" + nombre + "-" + t.getTaxiTaxi() + "\" value=\""+t.getTaxiTaxi()+"\" type=\"radio\" name=\"" + nombre + "\" class=\"" + (!requerido ? "nrequerido" : "requerido") + "\" />");
                out.println("</td>");
                out.println("<td class=\"ui-widget-content\">");
                out.println("<ul>");
                out.println("<li>Placa: " + t.getTaxiPlaca() + " </li>");
                out.println("<li>Conductor: " + usua.getUsuaNombre() + " " + usua.getUsuaApepat() + " " + usua.getUsuaApemat() + " </li>");
                out.println("<li>Modelo: " + t.getTaxiModelo() + "</li>");
                out.println("<li>Marca: " + t.getTaxiMarca() + "</li>");
                out.println("<li>Tipo taxi: " + tt.getTiptDescrip() + "</li>");
                out.println("<li>Ocupantes: " + t.getTaxiOcupantes() + "</li>");
                out.println("<li>Precio x KM : $" + tt.getTiptPrecio() + " </li>");
                out.println("<li>Descripci&oacute;n: " + t.getTaxiDescrip() + "</li>");
                out.println("</ul>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            dao.desconectar();

        } catch (IOException e) {
            throw new JspException(e);
        } catch (SQLException e) {
            throw new JspException(e);
        } catch (TaxiException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

}
