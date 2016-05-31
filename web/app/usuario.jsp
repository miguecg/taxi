<%-- 
    Document   : usuario
    Created on : 10-may-2016, 0:10:50
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="mx.taxi.beans.Usuario" %>
<%@page import="mx.taxi.beans.TaxiException" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="/taxi/jquery-ui/external/jquery/jquery.js"></script>
        <script type="text/javascript" src="/taxi/jquery-ui/jquery-ui.js"></script>
        <script type="text/javascript" src="/taxi/js/jquery.validaFormulario.js"></script>
        <link type="text/css" rel="stylesheet" href="/taxi/jquery-ui/jquery-ui.css"/>
        <title></title>
    </head>
    <body style="margin: 0 0 0 0">

        <%
            Long usuario = request.getParameter("v_idusuario") != null ? Long.valueOf(request.getParameter("v_idusuario")) : null;
            Usuario usu = new Usuario();
            String msg = null;
            if (usuario != null) {
                try {
                    usu.setId(usuario);
                    usu = usu.getUsuario();
                } catch (TaxiException e) {
                    msg = e.getMessage();
                }
            }

            if (msg == null) {
        %>

        <form name="uform">

            <table style="width:100%">
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        ID Usuario:                
                    </td>
                    <td class="ui-widget-content">
                        <% if (usuario != null) {%>
                        <%= usuario.toString()%>
                        <input type="hidden" name="v_idusuario" value="<%= usuario.toString()%>"/>
                        <% }%>
                    </td>
                </tr>  
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Usuario:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_usuario">  
                            <% if (usuario == null) {%> 
                            <input type="text" size="20" maxlength="32" name="v_usuario" value="<%= usuario != null ? usu.getUsuaUsuario() : ""%>" class="requerido"/>
                            <% } else {%>
                            <%= usu.getUsuaUsuario()%>
                            <input type="hidden" name="v_usuario" value="<%= usuario != null ? usu.getUsuaUsuario() : ""%>" class="requerido"/>
                            <% }%>
                            <div id="msg-v_usuario"></div>
                        </div>
                    </td>
                </tr>  
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Nombre:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_nombre">  
                            <input type="text" size="20" maxlength="32" name="v_nombre" value="<%= usuario != null ? usu.getUsuaNombre() : ""%>" class="requerido"/>
                            <div id="msg-v_nombre"></div>
                        </div>
                    </td>
                </tr> 
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Primer apellido:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_apepat">  
                            <input type="text" size="20" maxlength="32" name="v_apepat" value="<%= usuario != null ? usu.getUsuaApepat() : ""%>" class="requerido"/>
                            <div id="msg-v_apepat"></div>
                        </div>
                    </td>
                </tr> 
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Segundo apellido:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_apemat">  
                            <input type="text" size="20" maxlength="32" name="v_apemat" value="<%= usuario != null ? usu.getUsuaApemat() : ""%>" class="nrequerido"/>
                            <div id="msg-v_apemat"></div>
                        </div>
                    </td>
                </tr> 
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Email:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_email">  
                            <input type="text" size="20" maxlength="100" name="v_email" value="<%= usuario != null ? usu.getUsuaEmail() : ""%>" class="req-email"/>
                            <div id="msg-v_email"></div>
                        </div>
                    </td>
                </tr> 
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Tel&eacute;fono:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_telefono">  
                            <input type="text" size="20" maxlength="10" name="v_telefono" value="<%= usuario != null ? usu.getUsuaTelefono() : ""%>" class="requerido"/>
                            <div id="msg-v_telefono"></div>
                        </div>
                    </td>
                </tr> 

                <% if (usuario == null) { %>
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Contrase&ntilde;a:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_passuno">  
                            <input id="passuno" type="password" size="20" maxlength="32" name="v_passuno" class="requerido"/>
                            <div id="msg-v_passuno"></div>
                        </div>
                    </td>
                </tr> 
                <tr>
                    <td class="ui-widget-content" style="width:70px">
                        Repita la contrase&ntilde;a:                
                    </td>
                    <td class="ui-widget-content">
                        <div id="ca-v_passdos">  
                            <input id="passdos" type="password" size="20" maxlength="32" name="v_passdos" class="requerido"/>
                            <div id="msg-v_passdos"></div>
                        </div>
                    </td>
                </tr> 

                <% } %>

            </table>
            <div class="ui-widget-content" style="text-align:center">
                <script type="text/javascript">
                    $(function () {

                        $('#aUsua').on('click', function () {

                            if ($.validaFormulario({nfrom: 'uform'})) {
                                alert('Hay errores en sus datos !');
                            } else {

                                $.ajax({
                                    url: '/taxi/app/usuario.do',
                                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                                    data: $('form[name="uform"]').serialize(),
                                    type: 'POST',
                                    beforeSend: function () {

                                        $('#msg').dialog(
                                                {
                                                    resizable: false,
                                                    draggable: false,
                                                    modal: true,
                                                    closeOnScape: false
                                                }
                                        )
                                                .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>');
                                    }
                                }).done(function (html) {
                                    $('#principal').html(html);
                                    $('#vent').removeAttr('class').removeAttr('style').html('');
                                    $('.ui-dialog').each(function () {
                                        $(this).removeAttr('class').html('');
                                    });
                                    $('.ui-widget-overlay').removeAttr('class').removeAttr('style').attr('id', 'vent');
                                    $('#msg').dialog('close');
                                }).fail(function (jqXHR, textStatus) {
                                    $('#vent').html('<span style=\"padding:3px\">' + jqXHR.responseText + '</span>').addClass('ui-state-error ui-corner-all');
                                });
                            }
                        });
                    });
                </script>   
                <% if (usuario == null) { %>      

                <input id="aUsua" type="button" value="Agregar Usuario"/>
                <input type="hidden" name="opcion" value="agr"/>
                <% } else { %>
                <input id="aUsua" type="button" value="Actualizar Usuario"/>
                <input type="hidden" name="opcion" value="act"/>
                <% } %>
            </div>

        </form>

        <% } else {%>
        Error: <%= msg%>
        <% }%>
    </body>
</html>