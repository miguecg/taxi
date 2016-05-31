<%-- 
    Document   : index
    Created on : 10-may-2016, 0:13:15
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="mx.taxi.beans.Rol" %>
<%@page import="mx.taxi.beans.Usuario" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="//code.jquery.com/jquery-1.12.3.js"></script>
        <script type="text/javascript" src="/taxi/jquery-ui/jquery-ui.js"></script>
        <script type="text/javascript" src="/taxi/js/jquery.validaFormulario.js"></script>
        <script type="text/javascript" src="/taxi/js/utils.js"></script>
        <link type="text/css" rel="stylesheet" href="/taxi/jquery-ui/jquery-ui.css"/>
        <title></title>
    </head>
    <body>
        <div id="msg"></div>
        <div id="geo"></div>
        <div class="ui-corner-all ui-widget-header" style="width:650px;margin:auto">

            <script type="text/javascript">
                $(function () {

                    $('#hped').on('click', function () {
                        ir('/taxi/app/hacerPedido.jsp');
                    });

                    $('#ttx').on('click', function () {
                        ir('/taxi/app/ltaxi.jsp');
                    });
                    
                    $('#ttp').on('click', function () {
                        ir('/taxi/app/ltipoTaxi.jsp');
                    });

                    $('#usu').on('click', function () {
                        ir('/taxi/app/lusuario.jsp');
                    });
                                        
                });
            </script>
            <%-- 
               Rol r = new Rol();
               Long usuid = ((Usuario) request.getSession().getAttribute("USER_AUTH")).getUsuaUsuaid();
               List<Rol> lr = ((List<Rol>) request.getSession():getAttribute("USER_ROL"));
            --%>

            <input id="hped" type="button" value="Hacer pedido"/>
            
            <%-- if (!r.isRol(1l, lr)) { --%>
            <input id="ttx" type="button" value="Taxis"/>
            <input id="ttp" type="button" value="Tipo de Taxis"/>
            <input id="usu" type="button" value="Usuarios"/>
            <%-- } --%>
            
        </div>

        <p>
        <div id="principal" style="width:650px;margin:auto">
            <jsp:include page="lpedidos.jsp"/>
        </div>
    </p>
</body>
</html>
