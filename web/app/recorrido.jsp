<%-- 
    Document   : recorrido
    Created on : 31-may-2016, 10:13:40
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="mx.taxi.beans.Pedido" %>
<%@page import="mx.taxi.beans.Recorrido" %>
<%@page import="mx.taxi.beans.Usuario" %>
<%@page import="mx.taxi.beans.TaxiException" %>
<%
    Long ped = request.getParameter("v_pedido") != null ? Long.valueOf(request.getParameter("v_pedido")) : null;
    Pedido p = null;
    Usuario u = null;
    String msg = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    try {
        p = new Pedido();
        p.setId(ped);
        p = p.getPedido();
        u = new Usuario();
        u.setId(p.getPediUsuario());
        u = u.getUsuario();
    } catch (TaxiException e) {
        msg = e.getMessage();
    }

    if (ped != null && p != null) {

        Recorrido rec = new Recorrido();
        try {
            rec.setCondicion(" Where reco_pedido = '" + ped + "'  ");
        } catch (TaxiException e) {
            msg = e.toString();
        }
%>
<div class="ui-widget-content">
    <% if (rec != null && rec.getRecorridos().isEmpty()) { %>
    <script type="text/javascript">
        $('#initRec').on('click', function () {
            var longitud = 0;
            var latitud = 0;
            navigator.geolocation.getCurrentPosition(function (posicion) {
                latitud = posicion.coords.latitude;
                longitud = posicion.coords.longitude;

                $.ajax({
                    url: '/taxi/app/rec.do?opcion=agrInit&v_pedido=<%= p.getPediPedido() %>&v_latitud='+latitud+'&v_longitud='+longitud,
                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',                    
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
                                .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>')
                    }
                }).done(function (html) {
                    $('#msg').dialog('close');
                    $('#principal').html(html);
                }).fail(function (jqXHR, textStatus) {
                    $('#vent').html('<span style=\"padding:3px\">' + jqXHR.responseText + '</span>').addClass('ui-state-error ui-corner-all');
                });


            }, function (error) {
                alert('Ha ocurrido un error: ' + error.code + ': ' + error.message);
            });
        });
    </script>

    <input id="initRec" type="button" value="Iniciar Recorrido" />
    <% } %>
    <% if (rec != null && !rec.getRecorridos().isEmpty()) { %>
    <% if (rec.getRecorridos().size() > 1) { %>
    <script type="text/javascript">
        $('#initRec').on('click', function () {
            var longitud = 0;
            var latitud = 0;
            navigator.geolocation.getCurrentPosition(function (posicion) {
                latitud = posicion.coords.latitude;
                longitud = posicion.coords.longitude;

                $.ajax({
                    url: '/taxi/app/rec.do?opcion=saldar&v_pedido=<%= p.getPediPedido() %>',
                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',                    
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
                                .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>')
                    }
                }).done(function (html) {
                    $('#msg').dialog('close');
                    $('#principal').html(html);
                }).fail(function (jqXHR, textStatus) {
                    $('#vent').html('<span style=\"padding:3px\">' + jqXHR.responseText + '</span>').addClass('ui-state-error ui-corner-all');
                });


            }, function (error) {
                alert('Ha ocurrido un error: ' + error.code + ': ' + error.message);
            });
        });
    </script>
    <input type="button" value="Saldar"/>
    <% } else { %>
    <script type="text/javascript">
        $('#finalRec').on('click', function () {
            var longitud = 0;
            var latitud = 0;
            navigator.geolocation.getCurrentPosition(function (posicion) {
                latitud = posicion.coords.latitude;
                longitud = posicion.coords.longitude;

                $.ajax({
                    url: '/taxi/app/rec.do?opcion=agrFinal&v_pedido=<%= p.getPediPedido() %>&v_latitud='+latitud+'&v_longitud='+longitud,
                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',                    
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
                                .html('<span style="text-align:center,font-size:14px;font-weight:bold;margin:auto">Cargando, espere...</span><br/><p style="text-aling:center"><center><img src="/taxi/css/cargando.gif"/></center></p>')
                    }
                }).done(function (html) {
                    $('#msg').dialog('close');
                    $('#principal').html(html);
                }).fail(function (jqXHR, textStatus) {
                    $('#vent').html('<span style=\"padding:3px\">' + jqXHR.responseText + '</span>').addClass('ui-state-error ui-corner-all');
                });


            }, function (error) {
                alert('Ha ocurrido un error: ' + error.code + ': ' + error.message);
            });
        });
    </script>
    <input type="button" value="Finalizar Recorrido"/>
    <% } %>
    <% }%>
</div>

<table style="width:100%">
    <tr>
        <th class="ui-widget-header">Usuario</th>
        <th class="ui-widget-header">Fecha</th>
        <th class="ui-widget-header">Latitud</th>
        <th class="ui-widget-header">Longitud</th>
    </tr>
    <tr>
        <td class="ui-widget-content"><%= u.getUsuaNombre() + " " + u.getUsuaApepat() + " " + (u.getUsuaApemat() != null ? u.getUsuaApemat() : "")%></td>
        <td class="ui-widget-content"><%= sdf.format(p.getPediFecha())%></td>
        <td class="ui-widget-content"><%= p.getPediLatitud()%></td>
        <td class="ui-widget-content"><%= p.getPediLongitud()%></td>
    </tr>    
</table>

<div id="mapa">
    <jsp:include page="geo.jsp?v_latitud=<%=p.getPediLatitud().toString()%>&v_longitud=<%p.getPediLongitud().toString() %>"/>
</div>

<% }%>
