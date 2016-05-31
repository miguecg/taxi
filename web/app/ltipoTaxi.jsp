<%-- 
    Document   : ltipoTaxi
    Created on : 28-may-2016, 23:31:21
    Author     : miguel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="tipo" uri="/WEB-INF/tlds/tipotaxi.tld" %>
<script type="text/javascript" src="/taxi/js/utils.js"></script>
<script type="text/javascript">

    $(function () {

        $('#aTT').on('click', function () {
            irv('/taxi/app/tipoTaxi.jsp');
        });

        $('#mTT').on('click', function () {
            if ($('input[name="v_tipo"]:checked')) {
                irv('/taxi/app/tipoTaxi.jsp?v_tipo=' + $('input[name="v_tipo"]:checked').val());
            }
        });

    });

</script>
<div id="vent"></div>
<tipo:taxi nombre="v_tipo" requerido="<%= true %>" admin="<%= true %>"  /> 