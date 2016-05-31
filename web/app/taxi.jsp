<%-- 
    Document   : taxi
    Created on : 10-may-2016, 11:13:13
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="mx.taxi.beans.Taxi" %>
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
            Long tx = request.getParameter("v_taxi") != null ? Long.valueOf(request.getParameter("v_taxi")) : null;
            Taxi taxi = new Taxi();
            String msg = null;

            if (tx != null) {
                try {
                    taxi.setId(tx);
                    taxi = taxi.getTaxi();
                } catch (TaxiException e) {
                    msg = e.getMessage();
                }
            }

            if (msg == null) {
        %>

        <form name="tform">

            <table style="width:100%">                
                <tr>
                    <td class="ui-widget-content" style="width:100px">ID Taxi: </td>
                    <td class="ui-widget-content">

                        <% if (tx != null) {%>
                        <%= tx%>
                        <input type="hidden" value="<%= tx%>" name="v_taxi"/>                        
                        <% }%>

                    </td>
                </tr>
                <tr>
                    <td class="ui-widget-content" style="width:70px">Placa: </td>
                    <td class="ui-widget-content">                        
                        <div id="ca-v_placa">                            
                            <input type="text" name="v_placa" value="<%= tx != null ? taxi.getTaxiPlaca() : ""%>" size="12" maxlength="10" class="requerido"/>                              
                            <div id="msg-v_placa"></div>
                        </div>                        
                    </td>
                </tr>
                <tr>
                    <td class="ui-widget-content" style="width:70px">Modelo: </td>
                    <td class="ui-widget-content">                       
                        <div id="ca-v_modelo">                            
                            <input type="number" name="v_modelo" value="<%= tx != null ? taxi.getTaxiModelo() : 2000%>" size="12" maxlength="10" class="req-numero"/>                              
                            <div id="msg-v_modelo"></div>
                        </div>                        
                    </td>
                </tr>
                <tr>
                    <td class="ui-widget-content" style="width:70px">N. ocupantes: </td>
                    <td class="ui-widget-content">                       
                        <div id="ca-v_ocupantes">                            
                            <input type="number" name="v_ocupantes" value="<%= tx != null ? taxi.getTaxiOcupantes() : 1%>" size="12" maxlength="10" class="req-numero"/>                              
                            <div id="msg-v_ocupantes"></div>
                        </div>                        
                    </td>
                </tr>
                <tr>
                    <td class="ui-widget-content" style="width:70px">Tipo de taxi: </td>
                    <td class="ui-widget-content">                        
                        <div id="ca-v_tipotaxi">                            
                            <%@taglib prefix="tipo" uri="/WEB-INF/tlds/tipotaxi.tld" %>
                            <% if (tx == null) {%>
                            <tipo:taxi nombre="v_tipotaxi" requerido="<%= true%>" admin="<%= false %>"/>
                            <% } else {%>
                            <tipo:taxi nombre="v_tipotaxi" requerido="<%= true%>" select="<%= taxi.getTaxiTipotaxi()%>" admin="<%= false %>"/>
                            <% }%>
                            <div id="msg-v_tipotaxi"></div>
                        </div>                        
                    </td>
                </tr>

                <tr>
                    <td class="ui-widget-content" style="width:70px">Marca: </td>
                    <td class="ui-widget-content">                        
                        <div id="ca-v_marca">                                                        
                            <input type="text" name="v_marca" value="<%= tx != null ? taxi.getTaxiMarca() : ""%>" size="12" maxlength="50" class="requerido"/>
                            <div id="msg-v_marca"></div>
                        </div>                        
                    </td>
                </tr>


                <tr>
                    <td class="ui-widget-content" style="width:70px">Descripci&oacute;n: </td>
                    <td class="ui-widget-content">                        
                        <div id="ca-v_descrip">                                                        
                            <textarea name="v_descrip" style="width:150px;height:80px" class="requerido"><%= tx != null ? taxi.getTaxiDescrip() : ""%></textarea>                                                        
                            <div id="msg-v_descrip"></div>
                        </div>                        
                    </td>
                </tr>

                <tr>
                    <td class="ui-widget-content" style="width:70px">Usuario: </td>
                    <td class="ui-widget-content">                        
                        <div id="ca-v_usuario">                                                        
                            <%@taglib prefix="sel" uri="/WEB-INF/tlds/usua.tld" %>
                            <% if (tx == null) {%>
                            <sel:usuarios nombre="v_usuario" requerido="<%= true%>" />
                            <% } else {%>
                            <sel:usuarios nombre="v_usuario" requerido="<%= true%>" usuario="<%= tx != null ? taxi.getTaxiUsuario() : 0%>" />
                            <% } %>                            
                            <div id="msg-v_usuario"></div>
                        </div>                        
                    </td>
                </tr>                
            </table>
            <div style="width:100%" class="ui-widget-content">
                <script type="text/javascript">
                    $(function () {

                        $('#aTaxi').on('click', function () {

                            if ($.validaFormulario({nfrom: 'uform'})) {
                                alert('Hay errores en sus datos !');
                            } else {

                                $.ajax({
                                    url: '/taxi/app/taxi.do',
                                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                                    data: $('form[name="tform"]').serialize(),
                                    type: 'POST',
                                    beforeSend: function () {
                                        $('#msg').dialog(
                                                {
                                                    resizable: false,
                                                    draggable: false,
                                                    modal: true,
                                                    closeOnScape: false,
                                                    width: 500,
                                                    height: 600
                                                }
                                        )
                                                .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>')
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

                <% if (tx == null) { %>
                <input id="aTaxi" type="button" value="Agregar" />
                <input type="hidden" name="opcion" value="agr"/>
                <% } else { %>
                <input id="aTaxi" type="button" value="Actualizar" />
                <input type="hidden" name="opcion" value="act"/>
                <% } %>
            </div>    
        </form>

        <% } else {%>        
        <%= msg%>
        <% }%>

    </body>
</html>
