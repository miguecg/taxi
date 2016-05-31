<%-- 
    Document   : login
    Created on : 26-may-2016, 12:48:46
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <% if (request.getParameter("error") != null) { %>
        <div class="ui-state-error">
            ERROR: El usuario o la contrase&ntilde;a son incorrectos!!
        </div>
        <% } %>
        <form name="lform" method="POST" action="/taxi/app/index.jsp">
        <div class="ui-corner-all ui-widget-header" style="width:300px;margin:auto">
            <div class="ui-widget-header ui-corner-top">
                Iniciar Sesi&oacute;n:
            </div>
            <table style="width:100%">
                <tr>
                    <th class="ui-widget-content">
                        Usuario:
                    </th>
               
                    <th class="ui-widget-content">
                        <input type="text" name="v_usuario" size="20" maxlength="32" requerido/>
                    </th>
                </tr>
                <tr>
                    <th class="ui-widget-content">
                        Contrase&ntilde;a:
                    </th>
               
                    <th class="ui-widget-content">
                        <input type="password" name="v_password" size="20" maxlength="32" requerido/>
                    </th>
                </tr>
            </table>
            <div class="ui-widget-content">                
                <input id="ent" type="submit" value="Entrar" />
            </div>            
        </div>
        </form>
    </body>
</html>
