/*
 * Autor: Miguel Angel Cedeno Garciduenas
 * Email: miguecg@gmail.com 
 */

package mx.taxi.serv;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.taxi.beans.Usuario;
import mx.taxi.beans.Pedido;
import mx.taxi.beans.TaxiException;

/**
 *
 * @author miguel
 */
@WebServlet(name = "ServPedido", urlPatterns = {"/app/pedido.do"})
public class ServPedido extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("agr")) {

            Double lat = request.getParameter("v_lat") != null ? Double.valueOf(request.getParameter("v_lat")) : null;
            Double lon = request.getParameter("v_lon") != null ? Double.valueOf(request.getParameter("v_lon")) : null;
            Long taxi = request.getParameter("v_taxi") != null ? Long.valueOf(request.getParameter("v_lon")) : null;
            Long idUsua = ((Usuario) request.getSession().getAttribute("USER_AUTH")).getUsuaUsuaid();

            Pedido pedido = new Pedido();
            pedido.setPediLatitud(lat);
            pedido.setPediLongitud(lon);
            pedido.setPediUsuario(idUsua);
            pedido.setPediTaxi(taxi != null ? taxi : null);

            String msg = null;

            try {
                pedido.agrPedido(pedido);
            } catch (TaxiException e) {
                msg = e.getMessage();
            }

            if (msg == null) {

                this.VeaPagina("/app/lpedidos.jsp", request, response);
                
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.getSession().setAttribute("msg", "ERROR: "+msg);
                this.VeaPagina("/msg.jsp", request, response);
            }

        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            request.getSession().setAttribute("msg", "Algunos de los datos requeridos es nulo...");
            this.VeaPagina("/msg.jsp", request, response);
        }

    }

    private void VeaPagina(String direccion, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direccion);
        dispatcher.forward(request, response);
    }

}
