<%-- 
    Document   : ltaxi
    Created on : 17-may-2016, 0:41:38
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="taxi" uri="/WEB-INF/tlds/taxis.tld" %>
<script type="text/javascript" src="/taxi/js/utils.js"></script>
<script type="text/javascript">

    $(function () {

        $('#tAgr').on('click', function () {
            irv('/taxi/app/taxi.jsp');
        });

        $('#tAct').on('click', function () {
            if ($('input[name="v_taxi"]:checked')) {
                irv('/taxi/app/taxi.jsp?v_taxi=' + $('input[name="v_taxi"]:checked').val());
            }
        });

        $('#tAsigC').on('click', function () {
            if ($('input[name="v_taxi"]:checked')) {
                irv('/taxi/app/asigTaxi.jsp?v_taxi=' + $('input[name="v_taxi"]:checked').val());
            }
        });

    });

</script>
<div id="vent"></div>
<taxi:taxis nombre="v_taxi" admin="<%= true%>" requerido="<%= true%>"/>