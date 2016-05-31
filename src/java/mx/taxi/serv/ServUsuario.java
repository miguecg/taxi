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
import mx.taxi.beans.TaxiException;
import mx.taxi.beans.Usuario;
import mx.taxi.util.CifraMD5;

/**
 *
 * @author miguel
 */
@WebServlet(name = "ServUsuario", urlPatterns = {"/app/usuario.do"})
public class ServUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("agr")) {

            String usuario = request.getParameter("v_usuario") != null ? request.getParameter("v_usuario").trim() : null;
            String password = request.getParameter("v_passuno") != null ? request.getParameter("v_passuno").trim() : null;
            String nombre = request.getParameter("v_nombre") != null ? request.getParameter("v_nombre").trim() : null;
            String apepat = request.getParameter("v_apepat") != null ? request.getParameter("v_apepat").trim() : null;
            String apemat = request.getParameter("v_apemat") != null ? request.getParameter("v_apemat").trim() : null;
            String email = request.getParameter("v_email") != null ? request.getParameter("v_email").trim() : null;
            Long telefono = request.getParameter("v_telefono") != null ? Long.valueOf(request.getParameter("v_telefono").trim()) : null;

            if (usuario != null && password != null && nombre != null && apepat != null && apemat != null && email != null && telefono != null) {

                String msg = null;

                try {
                    Usuario usu = new Usuario();
                    usu.setCondicion(" Where usua_usuario = '"+usuario+"'");
                    
                    if (usu.getUsuario() != null) {
                        msg = "Ya existe este usuario...";
                    } else {
                        usu = new Usuario();
                        usu.setUsuaUsuario(usuario);
                        usu.setUsuaNombre(nombre);
                        usu.setUsuaApepat(apepat);
                        usu.setUsuaApemat(apemat);
                        usu.setUsuaEmail(email);
                        usu.setUsuaTelefono(telefono);
                        usu.setUsuaPassword(CifraMD5.hashPassword(password));
                        usu = usu.agrUsuario(usu);                        
                        
                        if (usu.getUsuaUsuaid() == null) {
                            msg = "Ha ocurrido un error!";
                        }
                    }
                    
                } catch (TaxiException e) {
                    msg = e.toString();
                }

                if (msg == null) {
                    this.VeaPagina("/app/lusuario.jsp", request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    request.setAttribute("msg", "ERROR: "+msg);
                    this.VeaPagina("/msg.jsp", request, response);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.setAttribute("msg", "ERROR: Faltan alguno datos!");
                this.VeaPagina("/msg.jsp", request, response);
            }

        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("act")) {
            
            Long idusuario = request.getParameter("v_idusuario") != null ? Long.valueOf(request.getParameter("v_idusuario")) : null;
            String nombre = request.getParameter("v_nombre") != null ? request.getParameter("v_nombre").trim() : null;
            String apepat = request.getParameter("v_apepat") != null ? request.getParameter("v_apepat").trim() : null;
            String apemat = request.getParameter("v_apemat") != null ? request.getParameter("v_apemat").trim() : null;
            String email = request.getParameter("v_email") != null ? request.getParameter("v_email").trim() : null;
            Long telefono = request.getParameter("v_telefono") != null ? Long.valueOf(request.getParameter("v_telefono").trim()) : null;

            
            if (idusuario != null && nombre != null && apepat != null && apemat != null && email != null && telefono != null) {

                String msg = null;

                try {
                    Usuario usu = new Usuario();
                    usu.setCondicion(" Where usua_id = '"+idusuario+"'");
                    
                    if (usu.getUsuario() == null) {
                        msg = "No existe este usuario...";
                    } else {
                        usu = new Usuario();
                        usu.setUsuaUsuaid(idusuario);
                        usu.setUsuaNombre(nombre);
                        usu.setUsuaApepat(apepat);
                        usu.setUsuaApemat(apemat);
                        usu.setUsuaEmail(email);
                        usu.setUsuaTelefono(telefono);
                        
                        usu.actDatosUsuario(usu);                        
                        
                    }
                    
                } catch (TaxiException e) {
                    msg = e.toString();
                }

                if (msg == null) {
                    this.VeaPagina("/app/lusuario.jsp", request, response);
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    request.setAttribute("msg", "ERROR: "+msg);
                    this.VeaPagina("/msg.jsp", request, response);
                }

            } else {
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                request.setAttribute("msg", "ERROR: Faltan alguno datos!");
                this.VeaPagina("/msg.jsp", request, response);
            }
            
        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("pass")) {
            
        } else if (request.getParameter("opcion") != null && request.getParameter("opcion").equals("stat")) {

        } else {
            response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
            request.setAttribute("msg", "ERROR: Objeto no encontrado!");
            this.VeaPagina("/msg.jsp", request, response);
        }
    }

    private void VeaPagina(String direccion,HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(direccion);
        dispatcher.forward(request, response);
    }

}
