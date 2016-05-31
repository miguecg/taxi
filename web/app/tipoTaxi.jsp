<%-- 
    Document   : tipoPedido
    Created on : 19-may-2016, 8:50:10
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="mx.taxi.beans.Tipotaxi" %>
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
            Long tipo = request.getParameter("v_tipo") != null ? Long.valueOf(request.getParameter("v_tipo")) : null;
            Tipotaxi tt = new Tipotaxi();
            String msg = null;

            if (tipo != null) {
                try {
                    tt.setId(tipo);
                    tt = tt.getTipoTaxi();
                } catch (TaxiException e) {
                    msg = e.toString();
                }
            }

            if (msg == null) {
        %>    

        <form name="uform">
            <table style="width:100%">
                <tr>
                    <td class="ui-widget-content">Tipo de taxi:</td>
                    <td class="ui-widget-content">
                        <% if (tipo != null) {%>
                        <input type="hidden" name="v_tipo" value="<%= tipo%>"/>
                        <%= tipo%>
                        <% }%>
                    </td>
                </tr>
                <tr>
                    <td class="ui-widget-content">Precio KM:</td>
                    <td class="ui-widget-content">
                        <div id="ca-v_precio"> 
                            <input type="text" name="v_precio" size="10" maxlength="8" value="<%= tipo != null ? tt.getTiptPrecio() : ""%>" required/>
                            <div id="msg-v_precio"></div>
                        </div>
                    </td>
                </tr>            
                <tr>
                    <td class="ui-widget-content">Descripci&oacute;n:</td>
                    <td class="ui-widget-content">
                        <div id="ca-v_descrip">
                            <textarea name="v_descrip" style="width:300px;height:100px" required><%= tipo != null ? tt.getTiptDescrip() : ""%></textarea>
                            <div id="msg-v_descrip"></div>
                        </div>
                    </td>
                </tr>            
                <tr>
                    <td class="ui-widget-content">Texto:</td>
                    <td class="ui-widget-content">
                        <div id="ca-v_texto">
                            <textarea name="v_texto" style="width:300px;height:100px" required><%= tipo != null ? tt.getTiptTexto() : ""%></textarea>
                            <div id="msg-v_texto"></div>
                        </div>
                    </td>
                </tr>            

            </table>    
            <div style="width:100%">
                <script type="text/javascript">
                    $(function () {
                        $('#aTipoTaxi').on('click', function () {
                            if ($.validaFormulario({nfrom: 'uform'})) {
                                alert('Hay errores en sus datos !');
                            } else {
                                $.ajax({
                                    url: '/taxi/app/tt.do',
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

                <% if (tipo == null) { %>
                <input id="aTipoTaxi" type="button" value="Agregar"/>
                <input type="hidden" name="opcion" value="agr"/>
                <% } else { %>
                <input id="aTipoTaxi" type="button" value="Actualizar"/>
                <input type="hidden" name="opcion" value="act"/>
                <% } %>

            </div>
        </form>
        <% }%>    
    </body>
</html>
