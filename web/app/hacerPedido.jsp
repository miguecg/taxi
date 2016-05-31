<%-- 
    Document   : hacerPedido
    Created on : 05-may-2016, 14:02:04
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="contenido">
    <div class="ui-widget-content ui-corner-top">Hacer Pedido de TAXI</div>
    <form name="pform">  
        <table style="width:100%">
            <tr>
                <th class="ui-widget-header">
                    Latitud
                </th>
                <th class="ui-widget-header">
                    Longitud
                </th>            
            </tr>
            <tr>
                <td class="ui-widget-content">
                    <div id="ca-v_lat"> 
                        <span id="lat"></span>
                        <input id="vlat" type="hidden" name="v_lat" value="" class="requerido"/>
                        <div id="msg-v_lat"></div>
                    </div>
                </td>
                <td class="ui-widget-content">
                    <div id="ca-v_lon">
                        <span id="lon"></span>
                        <input id="vlon" type="hidden" name="v_lon" value="" class="requerido"/>
                        <div id="msg-v_lon"></div>
                    </div>
                </td>            
            </tr>
        </table>
        <div class="ui-widget-header">

            <script type="text/javascript">
                $(function () {
                    $('#hped').on('click', function () {
                        
                        if ($.validaFormulario({nform: 'pform'})) {
                            alert('Hay errores en sus datos !');
                        } else {
                            $.ajax({
                                url: '/taxi/app/taxi.do',
                                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                                data: $('form[name="pform"]').serialize(),
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
                        }
                    });

                    $('#obtUbAct').on('click', function () {
                        var longitud = 0;
                        var latitud = 0;
                        navigator.geolocation.getCurrentPosition(function (posicion) {
                            latitud = posicion.coords.latitude;
                            longitud = posicion.coords.longitude;
                            $('#lat').html(latitud);
                            $('#vlat').val(latitud);
                            $('#vlon').val(longitud);
                            $('#lon').html(longitud);
                        }, function (error) {
                            alert('Ha ocurrido un error: ' + error.code + ': ' + error.message);
                        });
                    });

                    $('#obtUbMap').on('click', function () {
                        var longitud = $('#vlon').val();
                        var latitud = $('#vlat').val();
                        window.open('/taxi/app/geo.jsp?v_latitud=' + latitud + '&v_longitud=' + longitud, '', 'width=800,height=600,scrollbars=1');
                    });

                    $('#sTaxs').on('click', function () {
                        if ($('#sTaxs').val() === 'Cerrar') {
                            $('#sTaxs').val('Elegir Taxi');
                            $('#selTax').hide('slow');
                        } else {
                            $('#sTaxs').val('Cerrar');
                            $('#selTax').show('slow');
                        }
                    });
                });

                function modPosicion(lat, lon) {
                    $('#lat').html(lat);
                    $('#vlat').val(lat);
                    $('#lon').html(lon);
                    $('#vlon').html(lon);
                }
            </script>

            <input id="obtUbAct" type="button" value="Obtener Ubicaci&oacute;n actual"/> &nbsp; 
            <input id="obtUbMap" type="button" value="Obtener Ubicaci&oacute;n por mapa"/> &nbsp;
            <input id="sTaxs" type="button" value="Elegir Taxi"/>
        </div>
        <div class="ui-widget-content" id="selTax" style="display: none">
            <%@taglib prefix="taxi" uri="/WEB-INF/tlds/taxis.tld" %>
            <taxi:taxis nombre="v_taxi" admin="<%= false%>" requerido="<%= false%>"/>
        </div>
        <div class="ui-widget-content">

            <input type="hidden" name="opcion" value="agr"/>
            <input id="hped" type="button" value="Hacer pedido"/>
        </div>

    </form>
</div>