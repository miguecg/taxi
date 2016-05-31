<%-- 
    Document   : geo
    Created on : 10-may-2016, 12:14:12
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Double latitud = request.getParameter("v_latitud") != null ? Double.valueOf(request.getParameter("v_latitud")) : null;
    Double longitud = request.getParameter("v_longitud") != null ? Double.valueOf(request.getParameter("v_longitud")) : null;
%>
<% if (latitud != null && longitud != null) {%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="/taxi/jquery-ui/external/jquery/jquery.js"></script>
        <script type="text/javascript" src="/taxi/jquery-ui/jquery-ui.js"></script>
        <script type="text/javascript" src="/taxi/js/jquery.validaFormulario.js"></script>
        <link type="text/css" rel="stylesheet" href="/taxi/jquery-ui/jquery-ui.css"/>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false"></script>         
        <style>
            #map-canvas {
                width: 670px;
                height: 500px;
            }
            #current {
                padding-top: 25px;
            }
        </style>
        <script type="text/javascript" src="/ioem/js/ui/jquery.ui.map.js"></script>
        <script type="text/javascript">
            var marker;
            var map;
            function initialize() {
                var mapProp = {
                    center: new google.maps.LatLng(<%= latitud%>, <%= longitud%>),
                    zoom: 20,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                map = new google.maps.Map(document.getElementById("map-canvas"), mapProp);

                marker = new google.maps.Marker({
                    position: new google.maps.LatLng(<%= latitud%>, <%= longitud%>),
                    draggable: true
                });

                marker.setMap(map);
                var infoWindow = new google.maps.InfoWindow({
                    content: 'Sitio actual'
                });

                infoWindow.open(map, marker);

                google.maps.event.addListener(marker, 'dragend', function (evt) {
                    document.getElementById('cmg').innerHTML = '';
                    document.getElementById('current').innerHTML = '<span style=\"font-size:14px;color:red\">Nuevas coordenadas del marcador: <b>[ Latitud: ' + evt.latLng.lat().toFixed(10) + ', Longitud: ' + evt.latLng.lng().toFixed(10) + ' ]</b> <input id=\"vact\" type=\"button\" value=\"Click para cambiar posci&oacute;n geogr&aacute;fica\" onclick=\"cambiarUbicacion(' + evt.latLng.lat().toFixed(10) + ',' + evt.latLng.lng().toFixed(10) + ');\" /></span>';
                    document.getElementById('vlat').value = evt.latLng.lat().toFixed(10);
                    document.getElementById('vlon').value = evt.latLng.lng().toFixed(10);
                });
                google.maps.event.addListener(marker, 'dragstart', function (evt) {
                    document.getElementById('cmg').innerHTML = '<span style=\"font-size:14px;color:red;background-color:yellow\">Marcador en movimiento...</span>';
                });

            }
            google.maps.event.addDomListener(window, 'load', initialize);
            map.setCenter(marker.position);
            marker.setMap(map);


            function cambiarUbicacion(lati, longi) {
                if (confirm('Esto cambiar\u00e1 la ubicaci\u00f3n geogr\u00e1fica, esta seguro?')) {
                    window.opener.modPosicion(lati, longi);
                    var htm = " <br/><span style=\"font-size:14px;background-color:yellow;color:red\">Se actualiz&oacute; las coordenadas de la c&eacute;dula...</span>";
                    $('#cmg').html(htm);
                }
            }

        </script>

        <title></title>
    </head>
    <body>
        <div class="ui-widget-header" style="text-align:left">
            <input type="button" value="Cerrar" onclick="javascript:window.close();"/>
        </div>
        <p>
        <div id="cmg" style="width:600px; margin:auto;font-size:14px"></div>
        <div style="width:600px; margin:auto;font-size:14px">
            <br/> Coordenadas: <b>[ Latitud: <%= latitud%>, Longitud: <%= longitud%>]</b>
            <input id="clat" type="hidden" name="c_lat" value="<%= latitud%>"/>
            <input id="clon" type="hidden" name="c_lon" value="<%= longitud%>"/>           
        </div>
        <div id="current" style="width:600px; margin:auto;"></div>    
    </p>


    <div id="map-canvas" style="height:400px; width:600px; margin:auto"></div>
</body>
</html>
<% }%>