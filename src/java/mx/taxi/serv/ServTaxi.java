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
import mx.taxi.beans.Taxi;
import mx.taxi.beans.TaxiException;

/**
 *
 * @author miguel
 */
@WebServlet(name = "ServTaxi", urlPatterns = {"/app/taxi.do"})
public class ServTaxi extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("agr")) {

            String placa = request.getParameter("v_placa") != null ? request.getParameter("v_placa").trim() : null;
            Long ocupantes = request.getParameter("v_ocupantes") != null ? Long.valueOf(request.getParameter("v_ocupantes")) : null;
            Long tipoTaxi = request.getParameter("v_tipotaxi") != null ? Long.valueOf(request.getParameter("v_tipotaxi")) : null;
            String marca = request.getParameter("v_marca") != null ? request.getParameter("v_marca").trim() : null;
            Long usuario = request.getParameter("v_usuario") != null ? Long.valueOf(request.getParameter("v_usuario")) : null;
            String descrip = request.getParameter("v_descrip") != null ? request.getParameter("v_descrip").trim() : null;
            Long modelo = request.getParameter("v_modelo") != null ? Long.valueOf(request.getParameter("v_modelo")) : null;
            

            if (modelo != null && placa != null && ocupantes != null && tipoTaxi != null && marca != null && usuario != null) {

                Taxi taxi = new Taxi();
                taxi.setTaxiDescrip(descrip);
                taxi.setTaxiEstatus("A");
                taxi.setTaxiMarca(marca);
                taxi.setTaxiUsuario(usuario);
                taxi.setTaxiTipotaxi(tipoTaxi);
                taxi.setTaxiPlaca(placa);
                taxi.setTaxiModelo(modelo);
                taxi.setTaxiOcupantes(ocupantes);
                String msg = null;

                try {
                    taxi.agrTaxi(taxi);
                } catch (TaxiException e) {
                    msg = e.getMessage();
                }

                if (msg == null) {
                    this.VeaPagina("/app/ltaxi.jsp", request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    request.getSession().setAttribute("msg", msg);
                    this.VeaPagina("/msg.jsp", request, response);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.getSession().setAttribute("msg", "Algunos de los datos requeridos es nulo...");
                this.VeaPagina("/msg.jsp", request, response);
            }

        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("act")) {

            Long taxi = request.getParameter("v_taxi") != null ? Long.valueOf(request.getParameter("v_taxi")) : null;
            String placa = request.getParameter("v_placa") != null ? request.getParameter("v_placa").trim() : null;
            Long ocupantes = request.getParameter("v_ocupantes") != null ? Long.valueOf(request.getParameter("v_ocupantes")) : null;
            Long tipoTaxi = request.getParameter("v_tipotaxi") != null ? Long.valueOf(request.getParameter("v_tipotaxi")) : null;
            String marca = request.getParameter("v_marca") != null ? request.getParameter("v_marca").trim() : null;
            Long usuario = request.getParameter("v_usuario") != null ? Long.valueOf(request.getParameter("v_usuario")) : null;
            String descrip = request.getParameter("v_descrip") != null ? request.getParameter("v_descrip").trim() : null;
            Long modelo = request.getParameter("v_modelo") != null ? Long.valueOf(request.getParameter("v_modelo")) : null;
            String msg = null;

            if (modelo != null && taxi != null && placa != null && ocupantes != null && tipoTaxi != null && marca != null && usuario != null) {

                Taxi tax = new Taxi();
                tax.setTaxiDescrip(descrip);
                tax.setTaxiEstatus("A");
                tax.setTaxiMarca(marca);
                tax.setTaxiUsuario(usuario);
                tax.setTaxiTipotaxi(tipoTaxi);
                tax.setTaxiPlaca(placa);
                tax.setTaxiModelo(modelo);
                
                try {
                    tax.agrTaxi(tax);
                } catch (TaxiException e) {
                    msg = e.getMessage();
                }
                
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.getSession().setAttribute("msg", msg);
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
