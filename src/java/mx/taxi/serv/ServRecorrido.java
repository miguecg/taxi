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
import mx.taxi.beans.Recorrido;
import mx.taxi.beans.Pedido;
import mx.taxi.beans.TaxiException;

/**
 *
 * @author miguel
 */
@WebServlet(name = "ServRecorrido", urlPatterns = {"/app/rec.do"})
public class ServRecorrido extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("agrInit")) {
            Long pedido = request.getParameter("v_pedido") != null ? Long.valueOf(request.getParameter("v_pedido")) : null;
            Double lat = request.getParameter("v_latitud") != null ? Double.valueOf(request.getParameter("v_latitud")) : null;
            Double lon = request.getParameter("v_longitud") != null ? Double.valueOf(request.getParameter("v_longitud")) : null;
            String msg = null;

            try {
                Recorrido reco = new Recorrido();
                reco.agrInicioRecorrido(pedido, lat, lon);
            } catch (TaxiException e) {
                msg = e.toString();
            }

            if (msg == null) {
                this.VeaPagina("/app/recorrido.jsp", request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.getSession().setAttribute("msg", "ERROR: "+msg);
                this.VeaPagina("/msg.jsp", request, response);
            }

        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("agrFinal")) {
            Long pedido = request.getParameter("v_pedido") != null ? Long.valueOf(request.getParameter("v_pedido")) : null;
            Double lat = request.getParameter("v_latitud") != null ? Double.valueOf(request.getParameter("v_latitud")) : null;
            Double lon = request.getParameter("v_longitud") != null ? Double.valueOf(request.getParameter("v_longitud")) : null;
            String msg = null;
            
            try {
                Recorrido reco = new Recorrido();
                reco.agrFinRecorrido(pedido, lat, lon);
            } catch (TaxiException e) {
                msg = e.toString();
            }

            if (msg == null) {
                this.VeaPagina("/app/recorrido.jsp", request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.getSession().setAttribute("msg", "ERROR: "+msg);
                this.VeaPagina("/msg.jsp", request, response);
            }

        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("saldar")) {
            Long pedido = request.getParameter("v_pedido") != null ? Long.valueOf(request.getParameter("v_pedido")) : null;
            String msg = null;
            
            try {
                Pedido p = new Pedido();
                Float saldo = p.calcularPago(pedido);
                p.actMonto(pedido, saldo);              
            } catch (TaxiException e) {
                msg = e.toString();
            }

            if (msg == null) {
                this.VeaPagina("/app/recorrido.jsp", request, response);
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
