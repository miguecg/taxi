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
import mx.taxi.beans.Tipotaxi;
import mx.taxi.beans.TaxiException;

/**
 *
 * @author miguel
 */
@WebServlet(name = "ServTipoTaxi", urlPatterns = {"/app/tt.do"})
public class ServTipoTaxi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("agr")) {

            Float precio = request.getParameter("v_precio") != null ? Float.valueOf(request.getParameter("v_precio")) : null;
            String descrip = request.getParameter("v_descrip") != null ? request.getParameter("v_descrip").trim() : null;
            String texto = request.getParameter("v_texto") != null ? request.getParameter("v_texto").trim() : null;

            if (precio != null && descrip != null && texto != null) {

                Tipotaxi tt = new Tipotaxi();
                tt.setTiptDescrip(descrip);
                tt.setTiptPrecio(precio);
                tt.setTiptTexto(texto);
                String msg = null;

                try {
                    tt.agrTipotaxi(tt);
                } catch (TaxiException e) {
                    msg = e.toString();
                }

                if (msg == null) {
                    this.VeaPagina("/app/ltipoTaxi.jsp", request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    request.setAttribute("msg", msg);
                    this.VeaPagina("/msg.jsp", request, response);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.setAttribute("msg", "ERROR: Alguno de los datos es nulo!");
                this.VeaPagina("/msg.jsp", request, response);
            }

        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("act")) {
            Long idTipo = request.getParameter("v_tipo") != null ? Long.valueOf(request.getParameter("v_tipo")) : null;
            Float precio = request.getParameter("v_precio") != null ? Float.valueOf(request.getParameter("v_precio")) : null;
            String descrip = request.getParameter("v_descrip") != null ? request.getParameter("v_descrip").trim() : null;
            String texto = request.getParameter("v_texto") != null ? request.getParameter("v_texto").trim() : null;

            if (idTipo != null && precio != null && descrip != null && texto != null) {

                Tipotaxi tt = new Tipotaxi();
                tt.setTiptDescrip(descrip);
                tt.setTiptPrecio(precio);
                tt.setTiptTexto(texto);
                tt.setTiptTipotaxi(idTipo);
                String msg = null;

                try {
                    tt.actTipotaxi(tt);
                } catch (TaxiException e) {
                    msg = e.toString();
                }

                if (msg == null) {
                    this.VeaPagina("/app/ltipoTaxi.jsp", request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    request.setAttribute("msg", msg);
                    this.VeaPagina("/msg.jsp", request, response);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.setAttribute("msg", "ERROR: Alguno de los datos es nulo!");
                this.VeaPagina("/msg.jsp", request, response);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            request.setAttribute("msg", "ERROR: Objeto no encontrado!");
            this.VeaPagina("/msg.jsp", request, response);
        }

    }

    private void VeaPagina(String direccion, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direccion);
        dispatcher.forward(request, response);
    }

}
